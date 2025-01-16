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
import org.speak.easy.camera.capture.models.RecognizeTextStatus
import org.speak.easy.core.exstensions.launchSafe
import org.speak.easy.core.exstensions.onError
import org.speak.easy.core.navigation.Destination
import org.speak.easy.core.navigation.Navigator
import org.speak.easy.domain.TranslationRepository
import org.speak.easy.domain.models.SelectedLanguageDomain
import org.speak.easy.translator.api.SourceTextManager
import org.speak.easy.ui.components.models.LanguageUi
import org.speak.easy.core.ui.models.LanguageWithFlag
import org.speak.easy.permission.api.PermissionCallback
import org.speak.easy.permission.api.PermissionStatus
import org.speak.easy.permission.api.PermissionType

class CameraViewModel(
    private val translationRepository: TranslationRepository,
    private val sourceTextManager: SourceTextManager,
    private val navigator: Navigator,
) : ViewModel() {

    private val _showRationalDialog = MutableStateFlow(false)
    val showRationalDialog: StateFlow<Boolean> = _showRationalDialog

    private val _uiState = MutableStateFlow(CameraScreenUiState.unknown)
    val uiState: StateFlow<CameraScreenUiState> = _uiState.asStateFlow()

    init {
        translationRepository
            .observeCurrentLanguageData()
            .onEach(::updateSelectedLanguage)
            .onError {
                // TODO: Handle error
            }
            .launchIn(viewModelScope)
    }

    val permissionCallback = object : PermissionCallback {
        override fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus) {
            if (status != PermissionStatus.GRANTED) {
                _showRationalDialog.value = true
            }
        }
    }

    fun hideRationalDialog() {
        _showRationalDialog.value = false
    }

    fun handleStatus(status: RecognizeTextStatus) {
        when (status) {
            RecognizeTextStatus.Initial -> Unit
            RecognizeTextStatus.Progress -> Unit
            is RecognizeTextStatus.Success -> {
                sourceTextManager.setText(status.text)
                viewModelScope.launchSafe {
                    navigator.navigate(Destination.TranslatorGraph)
                }
            }

            RecognizeTextStatus.Error -> Unit
        }
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
            translationRepository.updateCurrentLanguage(selectedLanguage)
        }
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