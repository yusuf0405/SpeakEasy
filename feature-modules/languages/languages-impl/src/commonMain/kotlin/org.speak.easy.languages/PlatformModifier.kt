package org.speak.easy.languages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier

@Immutable
data class PlatformModifier(val modifier: Modifier)

@Composable
expect fun getPlatformModifier(): PlatformModifier