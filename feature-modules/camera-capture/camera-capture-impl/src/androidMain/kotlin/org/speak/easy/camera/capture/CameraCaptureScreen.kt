package org.speak.easy.camera.capture

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.speak.easy.camera.capture.managers.TextFromPhotoRecognize
import org.speak.easy.camera.capture.managers.TextFromPhotoRecognizeImpl
import org.speak.easy.camera.capture.models.CameraScreenUiState
import org.speak.easy.camera.capture.models.RecognizeTextStatus
import org.speak.easy.camera.capture.models.RecognizeTextStatus.*
import org.speak.easy.ui.components.components.LanguagesRow
import org.speak.easy.ui.core.theme.SpeakEasyTheme
import speakeasy.ui_core.generated.resources.Res
import speakeasy.ui_core.generated.resources.take_a_photo

@Composable
actual fun CameraCaptureScreen(
    modifier: Modifier,
    uiState: CameraScreenUiState,
    onSwapLanguage: () -> Unit,
    handleStatus: (RecognizeTextStatus) -> Unit
) {
    val context = LocalContext.current
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    val textFromPhotoRecognize: TextFromPhotoRecognize = remember {
        TextFromPhotoRecognizeImpl(context)
    }

    val status by textFromPhotoRecognize.status.collectAsStateWithLifecycle(Initial)

    LaunchedEffect(key1 = status) {
        handleStatus(status)
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidCamera(
            imageCapture = { imageCapture = it },
            modifier = Modifier
        )
        LanguagesRow(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(SpeakEasyTheme.dimens.dp16),
            isLoading = false,
            sourceLanguage = uiState.sourceLanguage,
            targetLanguage = uiState.targetLanguage,
            onClickTargetLanguage = { },
            onClickSourceLanguage = { },
            onSwapLanguages = onSwapLanguage
        )
        Button(
            onClick = { imageCapture?.let(textFromPhotoRecognize::recognize) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = SpeakEasyTheme.dimens.dp16),
            colors = ButtonColors(
                containerColor = SpeakEasyTheme.colors.backgroundModal,
                contentColor = SpeakEasyTheme.colors.textPrimary,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            )
        ) {
            Text(
                text = stringResource(Res.string.take_a_photo),
                style = SpeakEasyTheme.typography.bodyMedium.bold
            )
        }
    }
}