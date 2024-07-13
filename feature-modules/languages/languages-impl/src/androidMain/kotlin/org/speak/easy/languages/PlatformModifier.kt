package org.speak.easy.languages

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.speak.easy.ui.core.theme.SpeakEasyTheme

@Composable
private fun androidPlatformModifier() = PlatformModifier(
    modifier = Modifier
        .statusBarsPadding()
        .padding(top = SpeakEasyTheme.dimens.dp32)
)

@Composable
actual fun getPlatformModifier(): PlatformModifier = androidPlatformModifier()