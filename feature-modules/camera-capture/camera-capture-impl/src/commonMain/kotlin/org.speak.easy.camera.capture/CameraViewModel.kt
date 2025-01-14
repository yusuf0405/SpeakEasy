package org.speak.easy.camera.capture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.speak.easy.camera.capture.models.CameraScreenUiState
import org.speak.easy.core.exstensions.launchSafe
import org.speak.easy.core.exstensions.onError
import org.speak.easy.domain.TranslationRepository
import org.speak.easy.domain.models.SelectedLanguageDomain
import org.speak.easy.translator.api.SourceTextManager
import org.speak.easy.ui.components.models.LanguageUi
import org.speak.easy.core.ui.models.LanguageWithFlag

class CameraViewModel(
    private val translationRepository: TranslationRepository,
    private val sourceTextManager: SourceTextManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CameraScreenUiState.unknown)
    val uiState: StateFlow<CameraScreenUiState> = _uiState.asStateFlow()

    init {
        translationRepository
            .observeSelectedLanguageData()
            .onEach(::updateSelectedLanguage)
            .onError {
                // TODO: Handle error
            }
            .launchIn(viewModelScope)
    }

    fun onSwapLanguage() {
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

    fun setSourceText(text: String) {
        sourceTextManager.setText(text)
    }

    private fun updateSelectedLanguage(selectedLanguage: SelectedLanguageDomain) {
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
                )
            )
        }
    }
}