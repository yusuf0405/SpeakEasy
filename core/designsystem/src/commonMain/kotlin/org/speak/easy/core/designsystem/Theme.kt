package org.speak.easy.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
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
import org.speak.easy.core.designsystem.locale.AppLang
import org.speak.easy.core.designsystem.locale.rememberAppLocale
import org.speak.easy.core.designsystem.shapes.Shapes
import org.speak.easy.core.designsystem.typography.SpeakEasyTypography
import org.speak.easy.core.designsystem.typography.LocalSpeakEasyTypography
import org.speak.easy.core.designsystem.typography.ProvideTypography
import org.speak.easy.core.designsystem.typography.debugTypography

enum class ThemeType {
    LIGHT,
    DARK,
    SYSTEM
}

internal val LocalThemeIsDark = compositionLocalOf {
    mutableStateOf(true)
}

val LocalAppLocalization = compositionLocalOf {
    AppLang.English
}

private val defaultDimens = SpeakEasyDimens()

private val LocalDimens = staticCompositionLocalOf {
    defaultDimens
}

@Composable
fun SpeakEasyTheme(theme: ThemeType, content: @Composable () -> Unit) {
    val isDarkTheme = when (theme) {
        ThemeType.LIGHT -> false
        ThemeType.DARK -> true
        ThemeType.SYSTEM -> isSystemInDarkTheme()
    }
    val typography = SpeakEasyTypography()
    val colors = if (isDarkTheme) darkPalette else lightPalette
    val dimensionSet = remember { defaultDimens }
    val currentLanguage = rememberAppLocale()

    CompositionLocalProvider(LocalAppLocalization provides currentLanguage) {
        CompositionLocalProvider(
            staticCompositionLocalOf { dimensionSet } provides dimensionSet,
        ) {
            ProvideTypography(typography) {
                ProvideColors(colors) {
                    SystemAppearance(isDarkTheme)
                    MaterialTheme(
                        colorScheme = debugColors(isDarkTheme, darkPalette, lightPalette),
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
internal expect fun SystemAppearance(isDarkTheme: Boolean)