package org.speak.easy.camera.capture.managers

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.speak.easy.camera.capture.models.RecognizeTextStatus
import org.speak.easy.core.ui.models.PhotoType
import java.io.File

class TextFromPhotoRecognizeImpl(private val context: Context) : TextFromPhotoRecognize {

    private val mutableStatus = MutableStateFlow<RecognizeTextStatus>(RecognizeTextStatus.Initial)
    override val status: Flow<RecognizeTextStatus> = mutableStatus

    override fun recognize(imageCapture: ImageCapture) {
        mutableStatus.tryEmit(RecognizeTextStatus.Progress)
        val outputDirectory = context.filesDir
        val photoFile = File(outputDirectory, cratePhotoFilename())
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            OnImageSavedCallback(photoFile)
        )
    }

    private inner class OnImageSavedCallback(
        val photoFile: File
    ) : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
            val image = InputImage.fromFilePath(context, savedUri)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

            recognizer.process(image).addOnSuccessListener { visionText ->
                mutableStatus.tryEmit(RecognizeTextStatus.Success(visionText.text))
            }.addOnFailureListener { exception ->
                mutableStatus.tryEmit(RecognizeTextStatus.Error)
                Log.e("TextRecognition", "Error: ${exception.stackTraceToString()}")
            }
        }

        override fun onError(exception: ImageCaptureException) {
            mutableStatus.tryEmit(RecognizeTextStatus.Error)
            Log.e("TextRecognition", "Error: ${exception.stackTraceToString()}")
        }
    }

    private fun cratePhotoFilename() = "${System.currentTimeMillis()}." + org.speak.easy.core.ui.models.PhotoType.JPG.suffix
}