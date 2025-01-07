package org.speak.easy.ui.components.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.painterResource
import org.speak.easy.ui.components.models.LanguageUi
import org.speak.easy.ui.core.extensions.clickableNoRipple
import org.speak.easy.ui.core.theme.SpeakEasyTheme
import speakeasy.ui_core.generated.resources.Res
import speakeasy.ui_core.generated.resources.ic_round_swap

@Composable
fun LanguagesRow(
    isLoading: Boolean,
    sourceLanguage: LanguageUi,
    targetLanguage: LanguageUi,
    onClickTargetLanguage: () -> Unit,
    onClickSourceLanguage: () -> Unit,
    onSwapLanguages: () -> Unit,
    modifier: Modifier = Modifier
) {
    TranslatorCard(
        modifier = modifier,
        shape = RoundedCornerShape(size = SpeakEasyTheme.dimens.dp50)
    ) {
        Row(
            modifier = Modifier
                .padding(SpeakEasyTheme.dimens.dp16)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                ProgressBar(modifier = Modifier.weight(1f))
            } else {
                LanguageWithFlagComponent(
                    modifier = Modifier.weight(1f),
                    language = sourceLanguage,
                    textAlign = TextAlign.Start,
                    onClick = onClickSourceLanguage
                )
            }
            Image(
                modifier = Modifier.clickableNoRipple(onClick = onSwapLanguages),
                painter = painterResource(Res.drawable.ic_round_swap),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = SpeakEasyTheme.colors.iconsPrimary)
            )
            if (isLoading) {
                ProgressBar(
                    modifier = Modifier.weight(1f),
                    alignment = Alignment.CenterEnd
                )
            } else {
                LanguageWithFlagComponent(
                    modifier = Modifier.weight(1f),
                    language = targetLanguage,
                    isEndShow = true,
                    textAlign = TextAlign.End,
                    onClick = onClickTargetLanguage
                )
            }
        }
    }
}