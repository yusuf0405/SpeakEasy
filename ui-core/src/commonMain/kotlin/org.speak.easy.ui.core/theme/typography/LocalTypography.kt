package org.speak.easy.ui.core.theme.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSpeakEasyTypography = staticCompositionLocalOf<SpeakEasyTypography> {
    error("No typography provided")
}

@Composable
fun ProvideTypography(
    speakEasyTypography: SpeakEasyTypography,
    content: @Composable () -> Unit,
) {
    val styles = remember { speakEasyTypography }
    CompositionLocalProvider(
        values = arrayOf(LocalSpeakEasyTypography provides styles),
        content = content
    )
}