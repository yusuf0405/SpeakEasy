package org.speak.easy.core.designsystem.colors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSpeakEasyColors = staticCompositionLocalOf<SpeakEasyColors> {
    error("No palette provided")
}

@Composable
fun ProvideColors(
    colors: SpeakEasyColors,
    content: @Composable () -> Unit,
) {
    colors.update(colors)
    CompositionLocalProvider(
        values = arrayOf(LocalSpeakEasyColors provides colors),
        content = content
    )
}