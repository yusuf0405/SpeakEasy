package org.speak.easy.camera.capture

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

@Composable
internal fun AndroidCamera(
    imageCapture: (ImageCapture) -> Unit,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = modifier,
        factory = { context: Context ->
            val previewView: PreviewView = context.toPreviewView()
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener(
                {
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.surfaceProvider = previewView.surfaceProvider
                    }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    val imageCaptureUseCase = ImageCapture.Builder().build()

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner = lifecycleOwner,
                            cameraSelector = cameraSelector,
                            useCases = arrayOf(preview, imageCaptureUseCase)
                        )
                        imageCapture(imageCaptureUseCase)
                    } catch (exc: Exception) {
                        TODO("Добавить обработку ошибки")
                    }
                },
                ContextCompat.getMainExecutor(context)
            )
            previewView
        })
}

private fun Context.toPreviewView(): PreviewView {
    return PreviewView(this).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
    }
}