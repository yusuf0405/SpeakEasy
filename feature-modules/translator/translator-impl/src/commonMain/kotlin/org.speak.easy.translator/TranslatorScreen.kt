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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.speak.easy.languages.api.LanguageFeatureApi
import org.speak.easy.translator.components.MicrophoneRecordingAnimation
import org.speak.easy.ui.components.components.TranslatorCard
import org.speak.easy.translator.models.TranslatorScreenUiState
import org.speak.easy.ui.components.components.LanguagesRow
import org.speak.easy.ui.components.components.ProgressBar
import org.speak.easy.core.ui.extensions.SpacerHeight
import org.speak.easy.core.ui.extensions.SpacerWidth
import org.speak.easy.core.designsystem.SpeakEasyTheme
import org.speak.easy.core.designsystem.colors.ControlPrimaryActive
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.close_icon
import speakeasy.core.ui.generated.resources.content_copy
import speakeasy.core.ui.generated.resources.enter_the_text
import speakeasy.core.ui.generated.resources.fluent_speaker
import speakeasy.core.ui.generated.resources.share_fill
import speakeasy.core.ui.generated.resources.the_result_of_the_transfer
import speakeasy.core.ui.generated.resources.translate

@Composable
internal fun TranslatorScreen(
    modifier: Modifier = Modifier,
    uiState: TranslatorScreenUiState,
    sourceText: String,
    languageFeatureApi: LanguageFeatureApi,
    onAction: (TranslatorScreenAction) -> Unit
) {
    var openSourceBottomSheet by rememberSaveable { mutableStateOf(false) }
    var openTargetBottomSheet by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SpeakEasyTheme.colors.backgroundPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        if (openSourceBottomSheet) {
            languageFeatureApi.showLanguagesModal(
                isSourceLanguage = true,
                currentLanguage = uiState.sourceLanguage.name,
                otherLanguage = uiState.targetLanguage.name,
                onDismissRequest = {
                    openSourceBottomSheet = false
                },
                onClick = {
                    onAction(TranslatorScreenAction.OnSourceTextClick(it))
                    openSourceBottomSheet = false
                },
            )
        }

        if (openTargetBottomSheet) {
            languageFeatureApi.showLanguagesModal(
                isSourceLanguage = false,
                currentLanguage = uiState.targetLanguage.name,
                otherLanguage = uiState.sourceLanguage.name,
                onDismissRequest = {
                    openTargetBottomSheet = false
                },
                onClick = {
                    onAction(TranslatorScreenAction.OnTargetTextClick(it))
                    openTargetBottomSheet = false
                }
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
                    isLoading = uiState.isLoading,
                    sourceLanguage = uiState.sourceLanguage,
                    targetLanguage = uiState.targetLanguage,
                    onSwapLanguages = { onAction(TranslatorScreenAction.OnSwapLanguages) },
                    onClickSourceLanguage = { openSourceBottomSheet = !openSourceBottomSheet },
                    onClickTargetLanguage = { openTargetBottomSheet = !openTargetBottomSheet }
                )
                SpacerHeight(SpeakEasyTheme.dimens.dp20)
                SourceTextCard(
                    uiState = uiState,
                    onAction = onAction,
                    sourceText = sourceText
                )
                SpacerHeight(SpeakEasyTheme.dimens.dp26)
                TargetTextCard(
                    uiState = uiState,
                    sourceText = sourceText,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun SourceTextCard(
    uiState: TranslatorScreenUiState,
    sourceText: String,
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
                                    sourceText,
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
                value = sourceText,
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
    sourceText: String,
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
                if (uiState.targetText.isNotEmpty() && sourceText.isNotEmpty()) {
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