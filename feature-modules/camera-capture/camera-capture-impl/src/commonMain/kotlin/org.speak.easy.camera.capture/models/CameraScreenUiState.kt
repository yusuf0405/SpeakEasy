package org.speak.easy.camera.capture.models

import org.speak.easy.ui.components.models.LanguageUi

data class CameraScreenUiState(
    val sourceLanguage: LanguageUi,
    val targetLanguage: LanguageUi,
) {

    companion object {
        val unknown = CameraScreenUiState(
            sourceLanguage = LanguageUi.unknown,
            targetLanguage = LanguageUi.unknown
        )
    }
}