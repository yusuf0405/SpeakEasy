package org.speak.easy.translator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.speak.easy.core.ClipboardCopyManager
import org.speak.easy.core.TextSharingManager
import org.speak.easy.core.exstensions.launchSafe
import org.speak.easy.core.exstensions.onError
import org.speak.easy.domain.LanguageHistoryRepository
import org.speak.easy.domain.TranslationRepository
import org.speak.easy.domain.holders.LanguagesHolder
import org.speak.easy.domain.holders.LanguagesLoadState
import org.speak.easy.domain.models.LanguageDomain
import org.speak.easy.domain.models.SelectedLanguageDomain
import org.speak.easy.domain.models.TranslateRequestBodyDomain
import org.speak.easy.speech.api.SpeechRecognizerManager
import org.speak.easy.speech.api.SpeechRecognizerResult
import org.speak.easy.speech.api.TextToSpeechManager
import org.speak.easy.translator.api.SourceTextManager
import org.speak.easy.translator.models.LastLanguageState
import org.speak.easy.translator.models.RecordingStatus
import org.speak.easy.translator.models.TranslatorScreenUiState
import org.speak.easy.ui.components.mappers.LanguageDomainToUiMapper
import org.speak.easy.ui.components.models.LanguageUi
import org.speak.easy.ui.core.models.LanguageWithFlag

internal class TranslatorViewModel(
    private val translationRepository: TranslationRepository,
    private val languageHistoryRepository: LanguageHistoryRepository,
    private val languagesHolder: LanguagesHolder,
    private val speechRecognizerManager: SpeechRecognizerManager,
    private val textToSpeechManager: TextToSpeechManager,
    private val clipboardCopyManager: ClipboardCopyManager,
    private val textSharingManager: TextSharingManager,
    private val sourceTextManager: SourceTextManager,
    private val languageDomainToUiMapper: LanguageDomainToUiMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(TranslatorScreenUiState.unknown)
    val uiState: StateFlow<TranslatorScreenUiState> = _uiState.asStateFlow()

    val sourceText = sourceTextManager
        .sourceText
        .stateIn(viewModelScope, SharingStarted.Lazily, "")

    init {
        translationRepository
            .observeSelectedLanguageData()
            .onEach(::updateSelectedLanguage)
            .onError {
                // TODO: Handle error
            }
            .launchIn(viewModelScope)

        languagesHolder.languagesLoadStateFlow.onEach { state ->
            when (state) {
                is LanguagesLoadState.Loading -> {
                    _uiState.update { uiState ->
                        uiState.copy(isLoading = uiState.lastLanguageState != LastLanguageState.FROM_CACHE)
                    }
                }

                is LanguagesLoadState.Error -> {
                    // TODO: Handle error
                }

                is LanguagesLoadState.FinishError -> {
                    _uiState.update { uiState ->
                        uiState.copy(isError = uiState.lastLanguageState != LastLanguageState.FROM_CACHE)
                    }
                }

                is LanguagesLoadState.Success -> {
                    if (_uiState.value.lastLanguageState == LastLanguageState.FROM_CACHE) return@onEach
                    val languagesModel = state.languagesModel

                    val sourceLanguage = languagesModel
                        .sourceLanguages
                        .first()
                        .run(languageDomainToUiMapper::map)

                    val targetLanguage = languagesModel
                        .targetLanguages[1]
                        .run(languageDomainToUiMapper::map)

                    _uiState.update {
                        it.copy(
                            sourceLanguage = sourceLanguage,
                            targetLanguage = targetLanguage,
                            isLoading = false
                        )
                    }
                }

                is LanguagesLoadState.Initial -> Unit
            }
        }.onError {
            // TODO: Handle error
        }.launchIn(viewModelScope)

        speechRecognizerManager.observeSpeech().onEach { result ->
            _uiState.update {
                it.copy(recordingStatus = result.toRecordingStatus())
            }
            sourceTextManager.setText(
                if (result is SpeechRecognizerResult.Success) {
                    "${sourceText.value} ${result.text}"
                } else {
                    sourceText.value
                }
            )
        }.launchIn(viewModelScope)
    }

    private suspend fun updateSelectedLanguage(selectedLanguage: SelectedLanguageDomain) {
        if (selectedLanguage == SelectedLanguageDomain.unknown) return
        _uiState.update { state ->
            state.copy(
                sourceLanguage = LanguageUi(
                    languageCode = selectedLanguage.sourceLanguageCode,
                    name = selectedLanguage.sourceLanguage,
                    flag = LanguageWithFlag.find(selectedLanguage.sourceLanguageCode)
                ),
                targetLanguage = LanguageUi(
                    languageCode = selectedLanguage.targetLanguageCode,
                    name = selectedLanguage.targetLanguage,
                    flag = LanguageWithFlag.find(selectedLanguage.targetLanguageCode)
                ),
                lastLanguageState = LastLanguageState.FROM_CACHE
            )
        }
        languagesHolder.fetchLanguages()
    }

    fun onAction(action: TranslatorScreenAction) {
        when (action) {
            is TranslatorScreenAction.OnTranslateClick -> {
                doTranslateText()
            }

            is TranslatorScreenAction.OnSpeakClick -> {
                if (_uiState.value.recordingStatus == RecordingStatus.Recording) {
                    speechRecognizerManager.stopListening()
                    return
                }
                speechRecognizerManager.startListening(_uiState.value.sourceLanguage.languageCode)
            }

            is TranslatorScreenAction.OnSwapLanguages -> {
                val state = uiState.value
                val selectedLanguage = SelectedLanguageDomain(
                    targetLanguage = state.sourceLanguage.name,
                    targetLanguageCode = state.sourceLanguage.languageCode,
                    sourceLanguage = state.targetLanguage.name,
                    sourceLanguageCode = state.targetLanguage.languageCode
                )
                viewModelScope.launchSafe {
                    translationRepository.updateSelectedLanguage(selectedLanguage)
                }
            }

            is TranslatorScreenAction.OnClearText -> {
                sourceTextManager.setText("")
                _uiState.update { state -> state.copy(targetText = "") }
            }

            is TranslatorScreenAction.OnVoiceClick -> {
                if (action.text.isEmpty()) return
                textToSpeechManager.speak(
                    text = action.text,
                    languageCode = action.languageCode
                )
            }

            is TranslatorScreenAction.OnSourceTextChange -> {
                sourceTextManager.setText(action.text)
            }

            is TranslatorScreenAction.OnTargetTextClick -> {
                val state = uiState.value
                if (state.targetLanguage.name == action.language.name) return
                val selectedLanguage = SelectedLanguageDomain(
                    targetLanguage = action.language.name,
                    targetLanguageCode = action.language.languageCode,
                    sourceLanguage = state.targetLanguage.name,
                    sourceLanguageCode = state.targetLanguage.languageCode
                )
                viewModelScope.launchSafe {
                    translationRepository.updateSelectedLanguage(selectedLanguage)
                }

                doTranslateText()
                addLanguageToHistory(action.language)
            }

            is TranslatorScreenAction.OnSourceTextClick -> {
                val state = uiState.value
                if (state.sourceLanguage.name == action.language.name) return
                val selectedLanguage = SelectedLanguageDomain(
                    targetLanguage = state.targetLanguage.name,
                    targetLanguageCode = state.targetLanguage.languageCode,
                    sourceLanguage = action.language.name,
                    sourceLanguageCode = action.language.languageCode
                )
                viewModelScope.launchSafe {
                    translationRepository.updateSelectedLanguage(selectedLanguage)
                }
                addLanguageToHistory(action.language)
            }

            is TranslatorScreenAction.OnCopy -> {
                clipboardCopyManager.setClipboard(action.text)
            }

            is TranslatorScreenAction.OnShare -> {
                val shareText = "${sourceText.value}\n\n${_uiState.value.targetText}"
                textSharingManager.shareText(shareText)
            }
        }
    }

    private fun doTranslateText() {
        val sourceText = sourceText.value
        if (sourceText.isEmpty()) return
        val body = TranslateRequestBodyDomain(
            text = sourceText,
            targetLang = _uiState.value.targetLanguage.languageCode,
            sourceLang = _uiState.value.sourceLanguage.languageCode,
            sourceFullLang = _uiState.value.sourceLanguage.name,
            targetFullLang = _uiState.value.targetLanguage.name
        )

        viewModelScope.launchSafe {
            val result = translationRepository.translateText(body).first()
            _uiState.update { state ->
                state.copy(targetText = result.text)
            }
        }
    }

    private fun addLanguageToHistory(language: LanguageUi) {
        viewModelScope.launchSafe {
            val languageDomain = LanguageDomain(
                language = language.languageCode,
                name = language.name,
            )
            languageHistoryRepository.addLanguage(languageDomain)
        }
    }

    private fun SpeechRecognizerResult.toRecordingStatus(): RecordingStatus {
        return when (this) {
            is SpeechRecognizerResult.StartListening -> RecordingStatus.Recording
            is SpeechRecognizerResult.Error -> RecordingStatus.Error
            is SpeechRecognizerResult.StopListening -> RecordingStatus.Stopped
            is SpeechRecognizerResult.Success -> RecordingStatus.Stopped
        }
    }
}