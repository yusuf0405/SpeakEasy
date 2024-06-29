package org.speak.easy.translator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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
import org.speak.easy.translator.mappers.LanguageDomainToUiMapper
import org.speak.easy.translator.models.BottomSheetUiState
import org.speak.easy.translator.models.LanguageUi
import org.speak.easy.translator.models.LastLanguageState
import org.speak.easy.translator.models.RecordingStatus
import org.speak.easy.translator.models.TranslatorScreenUiState
import org.speak.easy.ui.core.models.LanguageWithFlag

private const val SEARCH_DEBOUNCE_MILLS = 200L

internal class TranslatorViewModel(
    private val translationRepository: TranslationRepository,
    private val languageHistoryRepository: LanguageHistoryRepository,
    private val languagesHolder: LanguagesHolder,
    private val speechRecognizerManager: SpeechRecognizerManager,
    private val textToSpeechManager: TextToSpeechManager,
    private val clipboardCopyManager: ClipboardCopyManager,
    private val textSharingManager: TextSharingManager,
    private val languageDomainToUiMapper: LanguageDomainToUiMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(TranslatorScreenUiState.unknown)
    val uiState: StateFlow<TranslatorScreenUiState> = _uiState.asStateFlow()

    private var _bottomSheetUiState = MutableStateFlow(BottomSheetUiState.unknown)
    val bottomSheetUiState: StateFlow<BottomSheetUiState> = _bottomSheetUiState.asStateFlow()

    private val searchQueryFlow = _bottomSheetUiState
        .map { it.searchQuery }
        .stateIn(viewModelScope, SharingStarted.Lazily, String())

    private var sourceLanguages: List<LanguageUi> = emptyList()
    private var targetLanguages: List<LanguageUi> = emptyList()

    init {
        viewModelScope.launchSafe {
            val selectedLanguage = translationRepository.fetchLatestSelectedLanguage()
            if (selectedLanguage != SelectedLanguageDomain.unknown) {
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
            }
            languagesHolder.fetchLanguages()
        }

        languageHistoryRepository.observeLanguageHistory()
            .map { it.map(languageDomainToUiMapper::map) }
            .onEach { languages ->
                _bottomSheetUiState.update { state ->
                    state.copy(historyLanguages = languages.toSet().toList())
                }
            }.onError {

            }.launchIn(viewModelScope)

        languagesHolder.languagesLoadStateFlow.onEach { state ->
            when (state) {
                is LanguagesLoadState.Loading -> {
                    _bottomSheetUiState.update { it.copy(isLoading = true) }
                    _uiState.update { uiState ->
                        uiState.copy(isLoading = uiState.lastLanguageState != LastLanguageState.FROM_CACHE)
                    }
                }

                is LanguagesLoadState.Error -> {

                }

                is LanguagesLoadState.FinishError -> {
                    _uiState.update { uiState ->
                        uiState.copy(isError = uiState.lastLanguageState != LastLanguageState.FROM_CACHE)
                    }
                    _bottomSheetUiState.update { it.copy(isError = true) }
                }

                is LanguagesLoadState.Success -> {
                    sourceLanguages = state.languagesModel.targetLanguages
                        .sortedBy { it.name }
                        .map(languageDomainToUiMapper::map)
                    targetLanguages = state.languagesModel.sourceLanguages
                        .sortedBy { it.name }
                        .map(languageDomainToUiMapper::map)

                    if (_uiState.value.lastLanguageState != LastLanguageState.FROM_CACHE) {
                        _uiState.update {
                            it.copy(
                                sourceLanguage = sourceLanguages.first(),
                                targetLanguage = targetLanguages[1],
                                isLoading = false
                            )
                        }
                    }
                    _bottomSheetUiState.update {
                        it.copy(
                            isLoading = false,
                            targetLanguages = targetLanguages,
                            sourceLanguages = sourceLanguages
                        )
                    }
                }

                is LanguagesLoadState.Initial -> Unit
            }
        }.onError {

        }.launchIn(viewModelScope)

        searchQueryFlow.debounce(SEARCH_DEBOUNCE_MILLS).onEach { query ->
            _bottomSheetUiState.update { state ->
                state.copy(
                    sourceLanguages = if (query.isEmpty()) {
                        sourceLanguages
                    } else {
                        sourceLanguages.search(query)
                    },
                    targetLanguages = if (query.isEmpty()) {
                        targetLanguages
                    } else {
                        targetLanguages.search(query)
                    }
                )
            }
        }.onError {

        }.launchIn(viewModelScope)

        _uiState.onEach { state ->
            val selectedLanguage = SelectedLanguageDomain(
                targetLanguage = state.targetLanguage.name,
                targetLanguageCode = state.targetLanguage.languageCode,
                sourceLanguage = state.sourceLanguage.name,
                sourceLanguageCode = state.sourceLanguage.languageCode
            )
            translationRepository.updateSelectedLanguage(selectedLanguage)
        }.onError {

        }.launchIn(viewModelScope)

        speechRecognizerManager.observeSpeech().onEach { result ->
            _uiState.update {
                it.copy(
                    recordingStatus = result.toRecordingStatus(),
                    sourceText = if (result is SpeechRecognizerResult.Success) {
                        "${it.sourceText} ${result.text}"
                    } else {
                        it.sourceText
                    }
                )
            }
        }.launchIn(viewModelScope)
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
                _uiState.update { state ->
                    state.copy(
                        targetLanguage = state.sourceLanguage,
                        sourceLanguage = state.targetLanguage,
                        targetText = state.sourceText,
                        sourceText = state.targetText
                    )
                }
            }

            is TranslatorScreenAction.OnClearText -> {
                _uiState.update { state ->
                    state.copy(sourceText = "", targetText = "")
                }
            }

            is TranslatorScreenAction.OnVoiceClick -> {
                if (action.text.isEmpty()) return
                textToSpeechManager.speak(
                    text = action.text,
                    languageCode = action.languageCode
                )
            }

            is TranslatorScreenAction.OnSourceTextChange -> {
                _uiState.update { state ->
                    state.copy(sourceText = action.text)
                }
            }

            is TranslatorScreenAction.OnTargetTextClick -> {
                _uiState.update { state ->
                    if (state.targetLanguage.name == action.language.name) return@update state
                    state.copy(targetLanguage = action.language)
                }
                doTranslateText()
                addLanguageToHistory(action.language)
            }

            is TranslatorScreenAction.OnSourceTextClick -> {
                _uiState.update { state ->
                    if (state.sourceLanguage.name == action.language.name) return@update state
                    state.copy(
                        sourceLanguage = action.language,
                    )
                }
                addLanguageToHistory(action.language)
            }

            is TranslatorScreenAction.OnSearch -> {
                _bottomSheetUiState.update { it.copy(searchQuery = action.query) }
            }

            is TranslatorScreenAction.OnClearSearchQuery -> {
                _bottomSheetUiState.update { it.copy(searchQuery = "") }
            }

            is TranslatorScreenAction.OnCopy -> {
                clipboardCopyManager.setClipboard(action.text)
            }

            is TranslatorScreenAction.OnShare -> {
                val shareText = "${_uiState.value.sourceText}\n\n${_uiState.value.targetText}"
                textSharingManager.shareText(shareText)
            }
        }
    }


    private fun doTranslateText() {
        val sourceText = _uiState.value.sourceText
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
            val language = LanguageDomain(
                language = language.languageCode,
                name = language.name,
            )
            languageHistoryRepository.addLanguage(language)
        }
    }

    private fun List<LanguageUi>.search(query: String): List<LanguageUi> = filter {
        it.name.contains(
            query,
            ignoreCase = true
        )
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