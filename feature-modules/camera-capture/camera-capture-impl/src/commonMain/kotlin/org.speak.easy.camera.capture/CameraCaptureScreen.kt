package org.speak.easy.camera.capture

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.speak.easy.camera.capture.models.CameraScreenUiState
import org.speak.easy.camera.capture.models.RecognizeTextStatus

@Composable
expect fun CameraCaptureScreen(
    modifier: Modifier,
    uiState: CameraScreenUiState,
    onSwapLanguage: () -> Unit,
    handleStatus: (RecognizeTextStatus) -> Unit
)