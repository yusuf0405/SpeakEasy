package org.speak.easy.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import org.speak.easy.core.designsystem.colors.SpeakEasyColors
import org.speak.easy.core.designsystem.colors.LocalSpeakEasyColors
import org.speak.easy.core.designsystem.colors.ProvideColors
import org.speak.easy.core.designsystem.colors.darkPalette
import org.speak.easy.core.designsystem.colors.debugColors
import org.speak.easy.core.designsystem.colors.lightPalette
import org.speak.easy.core.designsystem.dimens.SpeakEasyDimens
import org.speak.easy.core.designsystem.shapes.Shapes
import org.speak.easy.core.designsystem.typography.SpeakEasyTypography
import org.speak.easy.core.designsystem.typography.LocalSpeakEasyTypography
import org.speak.easy.core.designsystem.typography.ProvideTypography
import org.speak.easy.core.designsystem.typography.debugTypography

internal val LocalThemeIsDark = compositionLocalOf {
    mutableStateOf(true)
}

private val defaultDimens = SpeakEasyDimens()

private val LocalDimens = staticCompositionLocalOf {
    defaultDimens
}

@Composable
fun SpeakEasyTheme(content: @Composable () -> Unit) {
    val typography = SpeakEasyTypography()
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember { mutableStateOf(systemIsDark) }

    CompositionLocalProvider(LocalThemeIsDark provides isDarkState) {
        val isDark by isDarkState
        val colors = if (isDark) darkPalette else lightPalette
        val dimensionSet = remember { defaultDimens }

        CompositionLocalProvider(
            staticCompositionLocalOf { dimensionSet } provides dimensionSet,
        ) {
            ProvideTypography(typography) {
                ProvideColors(colors) {
                    SystemAppearance(!isDark)
                    MaterialTheme(
                        colorScheme = debugColors(isDark, darkPalette, lightPalette),
                        typography = debugTypography(),
                        shapes = Shapes,
                        content = content
                    )
                }
            }
        }
    }
}

object SpeakEasyTheme {
    val colors: SpeakEasyColors
        @Composable
        get() = LocalSpeakEasyColors.current

    val typography: SpeakEasyTypography
        @Composable
        get() = LocalSpeakEasyTypography.current

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    val dimens: SpeakEasyDimens
        @Composable
        get() = LocalDimens.current

}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)