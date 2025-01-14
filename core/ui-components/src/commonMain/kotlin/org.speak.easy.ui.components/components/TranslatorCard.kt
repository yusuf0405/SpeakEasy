package org.speak.easy.ui.components.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import org.speak.easy.core.designsystem.SpeakEasyTheme

@Composable
fun TranslatorCard(
    modifier: Modifier = Modifier,
    shape: Shape,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = SpeakEasyTheme.dimens.dp4,
        ),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = SpeakEasyTheme.colors.backgroundModal,
            contentColor = SpeakEasyTheme.colors.textPrimary
        ),
        content = content
    )
}