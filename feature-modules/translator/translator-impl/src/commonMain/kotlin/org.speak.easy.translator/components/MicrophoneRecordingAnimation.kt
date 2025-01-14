package org.speak.easy.translator.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import org.jetbrains.compose.resources.painterResource
import org.speak.easy.translator.models.RecordingStatus
import org.speak.easy.translator.models.getRecordingColorByStatus
import org.speak.easy.core.ui.extensions.clickableNoRipple
import org.speak.easy.core.designsystem.SpeakEasyTheme
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.voice

@Composable
fun MicrophoneRecordingAnimation(
    recordingStatus: RecordingStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    MicrophoneAnimationVisualizer(
        recordingStatus = recordingStatus,
        scale = scale,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun MicrophoneAnimationVisualizer(
    recordingStatus: RecordingStatus,
    scale: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isRecording = recordingStatus == RecordingStatus.Recording
    Box(
        modifier = modifier
            .scale(if (isRecording) scale else 1f)
            .background(
                color = getRecordingColorByStatus(recordingStatus),
                shape = CircleShape
            )
            .clickableNoRipple(onClick)
    ) {
        Image(
            modifier = Modifier.padding(SpeakEasyTheme.dimens.dp8),
            painter = painterResource(Res.drawable.voice),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = if (isRecording) SpeakEasyTheme.colors.backgroundPrimary
                else Color.White
            )
        )
    }
}