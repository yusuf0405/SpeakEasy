package org.speak.easy.camera.capture.managers

import androidx.camera.core.ImageCapture
import kotlinx.coroutines.flow.Flow
import org.speak.easy.camera.capture.models.RecognizeTextStatus

interface TextFromPhotoRecognize {
    val status: Flow<RecognizeTextStatus>
    fun recognize(imageCapture: ImageCapture)
}