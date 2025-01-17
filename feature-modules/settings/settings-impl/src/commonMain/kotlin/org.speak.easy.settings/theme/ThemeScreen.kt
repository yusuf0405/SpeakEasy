package org.speak.easy.settings.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.speak.easy.core.designsystem.SpeakEasyTheme
import org.speak.easy.core.designsystem.colors.ControlPrimaryActive
import org.speak.easy.core.ui.extensions.TrackScreenViewEvent
import org.speak.easy.domain.models.ThemeType
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.current_theme
import speakeasy.core.ui.generated.resources.dark
import speakeasy.core.ui.generated.resources.light
import speakeasy.core.ui.generated.resources.system

@Composable
internal fun ThemeScreen(
    currentTheme: ThemeType,
    onThemeChange: (ThemeType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeakEasyTheme.colors.backgroundPrimary)
            .padding(SpeakEasyTheme.dimens.dp16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(SpeakEasyTheme.dimens.dp32))
        ThemeToggleButton(currentTheme, onThemeChange)
        Spacer(modifier = Modifier.height(SpeakEasyTheme.dimens.dp32))
        Text(
            text = stringResource(Res.string.current_theme),
            style = SpeakEasyTheme.typography.bodyLarge.bold,
            color = SpeakEasyTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.height(SpeakEasyTheme.dimens.dp32))
    }
}

@Composable
private fun ThemeToggleButton(
    selectedTheme: ThemeType,
    onThemeChange: (ThemeType) -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(SpeakEasyTheme.dimens.dp12))
            .background(SpeakEasyTheme.colors.backgroundSecondary),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ThemeType.entries.forEach { theme ->
            val isSelected = theme == selectedTheme
            Button(
                modifier = Modifier.padding(horizontal = SpeakEasyTheme.dimens.dp6),
                onClick = { onThemeChange(theme) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) ControlPrimaryActive else Color.Transparent,
                ),
                shape = RoundedCornerShape(SpeakEasyTheme.dimens.dp8),
                contentPadding = PaddingValues(SpeakEasyTheme.dimens.dp0),
            ) {
                Text(
                    text = stringResource(theme.displayName()),
                    style = SpeakEasyTheme.typography.bodyMedium.bold,
                    color = if (isSelected) {
                        Color.White
                    } else {
                        SpeakEasyTheme.colors.textPrimary
                    }
                )
            }
        }
    }
    TrackScreenViewEvent(screenName = "change theme screen")
}

private fun ThemeType.displayName(): StringResource {
    return when (this) {
        ThemeType.LIGHT -> Res.string.light
        ThemeType.DARK -> Res.string.dark
        ThemeType.SYSTEM -> Res.string.system
    }
}
