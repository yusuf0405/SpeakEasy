package org.speak.easy.camera.capture.models

sealed interface RecognizeTextStatus {
    data object Initial : RecognizeTextStatus
    data object Progress : RecognizeTextStatus
    data class Success(val text: String) : RecognizeTextStatus
    data object Error : RecognizeTextStatus
}