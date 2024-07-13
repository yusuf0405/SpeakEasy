package org.speak.easy.translator.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import org.speak.easy.ui.components.models.LanguageUi
import org.speak.easy.ui.core.components.FlagItem
import org.speak.easy.ui.core.extensions.SpacerWidth
import org.speak.easy.ui.core.extensions.clickableNoRipple
import org.speak.easy.ui.core.theme.SpeakEasyTheme

@Composable
internal fun LanguageWithFlagComponent(
    language: LanguageUi,
    onClick: () -> Unit,
    textAlign: TextAlign,
    isEndShow: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickableNoRipple(onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (language.flag != null && !isEndShow) {
            FlagItem(language.flag!!)
            SpacerWidth(SpeakEasyTheme.dimens.dp10)
        }
        Text(
            modifier = Modifier.weight(1f),
            text = language.name,
            style = SpeakEasyTheme.typography.bodyLarge.bold,
            textAlign = textAlign,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1

        )
        if (language.flag != null && isEndShow) {
            SpacerWidth(SpeakEasyTheme.dimens.dp10)
            FlagItem(language.flag!!)
        }
    }
}