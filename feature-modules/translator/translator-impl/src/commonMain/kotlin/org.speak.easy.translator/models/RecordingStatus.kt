package org.speak.easy.translator.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.speak.easy.ui.core.theme.SpeakEasyTheme
import org.speak.easy.ui.core.theme.colors.ControlPrimaryActiveDark

enum class RecordingStatus {
    Recording,
    Stopped,
    Error
}

@Composable
fun getRecordingColorByStatus(status: RecordingStatus): Color {
    return when (status) {
        RecordingStatus.Recording -> SpeakEasyTheme.colors.onBackgroundPrimary
        RecordingStatus.Stopped -> SpeakEasyTheme.colors.textPrimaryLink
        RecordingStatus.Error -> ControlPrimaryActiveDark
    }
}