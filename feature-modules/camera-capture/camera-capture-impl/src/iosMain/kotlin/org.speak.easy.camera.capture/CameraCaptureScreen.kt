package org.speak.easy.camera.capture

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.speak.easy.camera.capture.models.CameraScreenUiState
import org.speak.easy.camera.capture.models.RecognizeTextStatus
import org.speak.easy.core.designsystem.SpeakEasyTheme

@Composable
actual fun CameraCaptureScreen(
    modifier: Modifier,
    uiState: CameraScreenUiState,
    onSwapLanguage: () -> Unit,
    handleStatus: (RecognizeTextStatus) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Not implemented for IOS",
            style = SpeakEasyTheme.typography.titleMedium.bold
        )
    }
}