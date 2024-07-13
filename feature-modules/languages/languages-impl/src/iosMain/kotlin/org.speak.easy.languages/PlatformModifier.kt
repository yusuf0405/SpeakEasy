package org.speak.easy.languages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val IosPlatformModifier = PlatformModifier(
    modifier = Modifier
)

@Composable
actual fun getPlatformModifier(): PlatformModifier = IosPlatformModifier