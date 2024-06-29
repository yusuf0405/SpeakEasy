package org.speak.easy.translator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.speak.easy.translator.components.LanguageWithFlagComponent
import org.speak.easy.translator.components.LanguagesModalBottomSheet
import org.speak.easy.translator.components.MicrophoneRecordingAnimation
import org.speak.easy.translator.components.TranslatorCard
import org.speak.easy.translator.models.BottomSheetUiState
import org.speak.easy.translator.models.TranslatorScreenUiState
import org.speak.easy.ui.core.extensions.SpacerHeight
import org.speak.easy.ui.core.extensions.SpacerWidth
import org.speak.easy.ui.core.extensions.clickableNoRipple
import org.speak.easy.ui.core.theme.SpeakEasyTheme
import org.speak.easy.ui.core.theme.colors.ControlPrimaryActive
import speakeasy.ui_core.generated.resources.Res
import speakeasy.ui_core.generated.resources.close_icon
import speakeasy.ui_core.generated.resources.content_copy
import speakeasy.ui_core.generated.resources.enter_the_text
import speakeasy.ui_core.generated.resources.fluent_speaker
import speakeasy.ui_core.generated.resources.ic_round_swap
import speakeasy.ui_core.generated.resources.share_fill
import speakeasy.ui_core.generated.resources.the_result_of_the_transfer
import speakeasy.ui_core.generated.resources.translate

@Composable
internal fun TranslatorScreen(
    modifier: Modifier = Modifier,
    uiState: TranslatorScreenUiState,
    bottomSheetUiState: BottomSheetUiState,
    onAction: (TranslatorScreenAction) -> Unit
) {
    var openSourceBottomSheet by rememberSaveable { mutableStateOf(false) }
    var openTargetBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SpeakEasyTheme.colors.backgroundPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        if (openSourceBottomSheet) {
            LanguagesModalBottomSheet(
                uiState = bottomSheetUiState,
                languages = bottomSheetUiState.sourceLanguages,
                currentLanguage = uiState.sourceLanguage.name,
                otherLanguage = uiState.targetLanguage.name,
                modifier = Modifier.align(Alignment.BottomCenter),
                bottomSheetState = bottomSheetState,
                onDismissRequest = {
                    onAction(TranslatorScreenAction.OnClearSearchQuery)
                    openSourceBottomSheet = false
                },
                onClick = {
                    onAction(TranslatorScreenAction.OnSourceTextClick(it))
                    onAction(TranslatorScreenAction.OnClearSearchQuery)
                    openSourceBottomSheet = false
                },
                onSearchValueChange = { onAction(TranslatorScreenAction.OnSearch(it)) }
            )
        }

        if (openTargetBottomSheet) {
            LanguagesModalBottomSheet(
                uiState = bottomSheetUiState,
                languages = bottomSheetUiState.targetLanguages,
                currentLanguage = uiState.targetLanguage.name,
                otherLanguage = uiState.sourceLanguage.name,
                modifier = Modifier.align(Alignment.BottomCenter),
                bottomSheetState = bottomSheetState,
                onDismissRequest = {
                    openTargetBottomSheet = false
                    onAction(TranslatorScreenAction.OnClearSearchQuery)
                },
                onClick = {
                    onAction(TranslatorScreenAction.OnTargetTextClick(it))
                    onAction(TranslatorScreenAction.OnClearSearchQuery)
                    openTargetBottomSheet = false
                },
                onSearchValueChange = { onAction(TranslatorScreenAction.OnSearch(it)) }
            )
        }
        if (uiState.isError) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = SpeakEasyTheme.colors.onBackgroundPrimary
                )
            }
        } else {
            Column(modifier = Modifier.padding(SpeakEasyTheme.dimens.dp16)) {
                LanguagesRow(
                    uiState = uiState,
                    onAction = onAction,
                    onDismissSourceBottomSheet = { openSourceBottomSheet = !openSourceBottomSheet },
                    onDismissTargetBottomSheet = { openTargetBottomSheet = !openTargetBottomSheet }
                )
                SpacerHeight(SpeakEasyTheme.dimens.dp20)
                SourceTextCard(
                    uiState = uiState,
                    onAction = onAction,
                )
                SpacerHeight(SpeakEasyTheme.dimens.dp26)
                TargetTextCard(
                    uiState = uiState,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun LanguagesRow(
    uiState: TranslatorScreenUiState,
    onAction: (TranslatorScreenAction) -> Unit,
    onDismissTargetBottomSheet: () -> Unit,
    onDismissSourceBottomSheet: () -> Unit,
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
            if (uiState.isLoading) {
                ProgressBar(modifier = Modifier.weight(1f))
            } else {
                LanguageWithFlagComponent(
                    modifier = Modifier.weight(1f),
                    language = uiState.sourceLanguage,
                    textAlign = TextAlign.Start,
                    onClick = onDismissSourceBottomSheet
                )
            }
            Image(
                modifier = Modifier.clickableNoRipple {
                    onAction(TranslatorScreenAction.OnSwapLanguages)
                },
                painter = painterResource(Res.drawable.ic_round_swap),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = SpeakEasyTheme.colors.iconsPrimary)
            )
            if (uiState.isLoading) {
                ProgressBar(
                    modifier = Modifier.weight(1f),
                    alignment = Alignment.CenterEnd
                )
            } else {
                LanguageWithFlagComponent(
                    modifier = Modifier.weight(1f),
                    language = uiState.targetLanguage,
                    isEndShow = true,
                    textAlign = TextAlign.End,
                    onClick = onDismissTargetBottomSheet
                )
            }
        }
    }
}

@Composable
private fun SourceTextCard(
    uiState: TranslatorScreenUiState,
    onAction: (TranslatorScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    TranslatorCard(
        modifier = modifier,
        shape = RoundedCornerShape(size = SpeakEasyTheme.dimens.dp16)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpeakEasyTheme.dimens.dp16)
        ) {
            if (uiState.isLoading) {
                ProgressBar()
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = uiState.sourceLanguage.name,
                        style = SpeakEasyTheme.typography.bodyLarge.bold,
                        textAlign = TextAlign.Start,
                        color = SpeakEasyTheme.colors.textPrimaryLink
                    )
                    SpacerWidth(SpeakEasyTheme.dimens.dp11)
                    Image(
                        modifier = Modifier.clickable {
                            onAction(
                                TranslatorScreenAction.OnVoiceClick(
                                    uiState.sourceText,
                                    uiState.sourceLanguage.languageCode
                                )
                            )
                        },
                        painter = painterResource(Res.drawable.fluent_speaker),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = SpeakEasyTheme.colors.textPrimaryLink)
                    )
                    Spacer(Modifier.weight(1f))
                    Image(
                        modifier = Modifier.clickable { onAction(TranslatorScreenAction.OnClearText) },
                        painter = painterResource(Res.drawable.close_icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = SpeakEasyTheme.colors.iconsPrimary)
                    )
                }
            }
            SpacerHeight(SpeakEasyTheme.dimens.dp16)
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = (-16).dp),
                value = uiState.sourceText,
                onValueChange = { onAction(TranslatorScreenAction.OnSourceTextChange(it)) },
                textStyle = SpeakEasyTheme.typography.bodyMedium.medium,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = SpeakEasyTheme.colors.textPrimary,
                    unfocusedTextColor = SpeakEasyTheme.colors.textPrimary,
                ),
                placeholder = {
                    Text(
                        text = stringResource(Res.string.enter_the_text),
                        color = SpeakEasyTheme.colors.textSecondary,
                        style = SpeakEasyTheme.typography.bodyMedium.medium
                    )
                }
            )
            SpacerHeight(SpeakEasyTheme.dimens.dp80)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MicrophoneRecordingAnimation(
                    recordingStatus = uiState.recordingStatus,
                    onClick = { onAction(TranslatorScreenAction.OnSpeakClick) }
                )
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(ControlPrimaryActive)
                        .clickable { onAction(TranslatorScreenAction.OnTranslateClick) }
                ) {
                    Text(
                        modifier = Modifier.padding(
                            horizontal = SpeakEasyTheme.dimens.dp24,
                            vertical = SpeakEasyTheme.dimens.dp10
                        ),
                        text = stringResource(Res.string.translate),
                        style = SpeakEasyTheme.typography.bodyMedium.bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun TargetTextCard(
    uiState: TranslatorScreenUiState,
    onAction: (TranslatorScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    TranslatorCard(
        modifier = modifier,
        shape = RoundedCornerShape(size = SpeakEasyTheme.dimens.dp16)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpeakEasyTheme.dimens.dp16)
        ) {
            if (uiState.isLoading) {
                ProgressBar()
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = uiState.targetLanguage.name,
                        style = SpeakEasyTheme.typography.bodyLarge.bold,
                        textAlign = TextAlign.Start,
                        color = SpeakEasyTheme.colors.textPrimaryLink
                    )
                    SpacerWidth(SpeakEasyTheme.dimens.dp11)
                    Image(
                        modifier = Modifier.clickable {
                            onAction(
                                TranslatorScreenAction.OnVoiceClick(
                                    uiState.targetText,
                                    uiState.targetLanguage.languageCode
                                )
                            )
                        },
                        painter = painterResource(Res.drawable.fluent_speaker),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = SpeakEasyTheme.colors.textPrimaryLink)
                    )
                }
            }
            SpacerHeight(SpeakEasyTheme.dimens.dp16)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = uiState.targetText.ifEmpty {
                    stringResource(Res.string.the_result_of_the_transfer)
                },
                style = SpeakEasyTheme.typography.bodyMedium.medium,
                textAlign = TextAlign.Start,
                color = if (uiState.targetText.isEmpty()) {
                    SpeakEasyTheme.colors.textSecondary
                } else {
                    SpeakEasyTheme.colors.textPrimary
                }
            )
            SpacerHeight(SpeakEasyTheme.dimens.dp80)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    modifier = Modifier
                        .padding(horizontal = SpeakEasyTheme.dimens.dp8)
                        .clickable { onAction(TranslatorScreenAction.OnCopy(uiState.targetText)) },
                    painter = painterResource(Res.drawable.content_copy),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = SpeakEasyTheme.colors.textPrimaryLink)
                )
                if (uiState.targetText.isNotEmpty() && uiState.sourceText.isNotEmpty()) {
                    SpacerWidth(SpeakEasyTheme.dimens.dp8)
                    Image(
                        modifier = Modifier
                            .padding(horizontal = SpeakEasyTheme.dimens.dp8)
                            .clickable { onAction(TranslatorScreenAction.OnShare) },
                        painter = painterResource(Res.drawable.share_fill),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = SpeakEasyTheme.colors.textPrimaryLink)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProgressBar(
    size: Dp = SpeakEasyTheme.dimens.dp24,
    alignment: Alignment = Alignment.CenterStart,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .align(alignment),
            strokeWidth = SpeakEasyTheme.dimens.dp2
        )
    }
}