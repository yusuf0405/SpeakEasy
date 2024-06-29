package org.speak.easy.translator.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.speak.easy.translator.models.BottomSheetUiState
import org.speak.easy.translator.models.LanguageUi
import org.speak.easy.ui.core.components.SearchTextField
import org.speak.easy.ui.core.extensions.SpacerHeight
import org.speak.easy.ui.core.extensions.SpacerWidth
import org.speak.easy.ui.core.extensions.clickableNoRipple
import org.speak.easy.ui.core.theme.SpeakEasyTheme
import speakeasy.ui_core.generated.resources.Res
import speakeasy.ui_core.generated.resources.all_languages
import speakeasy.ui_core.generated.resources.check_icon
import speakeasy.ui_core.generated.resources.default_error_text
import speakeasy.ui_core.generated.resources.enter_the_text
import speakeasy.ui_core.generated.resources.recently_used

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Preview
internal fun LanguagesModalBottomSheet(
    uiState: BottomSheetUiState,
    languages: List<LanguageUi>,
    currentLanguage: String,
    otherLanguage: String,
    bottomSheetState: SheetState,
    onDismissRequest: () -> Unit,
    onClick: (LanguageUi) -> Unit,
    onSearchValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        modifier = modifier.fillMaxSize(),
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState,
        containerColor = SpeakEasyTheme.colors.backgroundModal,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if (uiState.isError) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(top = SpeakEasyTheme.dimens.dp80)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.default_error_text),
                            style = SpeakEasyTheme.typography.titleMedium.bold,
                        )
                    }
                }
                return@LazyColumn
            }

            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(top = SpeakEasyTheme.dimens.dp80)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = SpeakEasyTheme.colors.onBackgroundPrimary)
                    }
                }
                return@LazyColumn
            }
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SpeakEasyTheme.colors.backgroundModal)
                        .padding(horizontal = SpeakEasyTheme.dimens.dp16)
                        .padding(top = SpeakEasyTheme.dimens.dp8)
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = SpeakEasyTheme.dimens.dp4
                        ),
                        shape = RoundedCornerShape(SpeakEasyTheme.dimens.dp8)
                    ) {
                        SearchTextField(
                            value = uiState.searchQuery,
                            onValueChange = onSearchValueChange
                        )
                    }
                }
            }
            if (uiState.historyLanguages.isNotEmpty()) {
                item {
                    LanguageTitle(
                        modifier = Modifier.padding(top = SpeakEasyTheme.dimens.dp16),
                        text = stringResource(Res.string.recently_used)
                    )
                }
                items(
                    items = uiState.historyLanguages,
                    key = { it.languageCode }
                ) { language ->

                    LanguageItem(
                        language = language,
                        onClick = onClick,
                        currentLanguage = currentLanguage,
                        otherLanguage = otherLanguage,
                        modifier = Modifier.animateItemPlacement()
                    )
                }
                item {
                    SpacerHeight(SpeakEasyTheme.dimens.dp16)
                }
            }
            item {
                LanguageTitle(
                    modifier = Modifier.padding(vertical = SpeakEasyTheme.dimens.dp8),
                    text = stringResource(Res.string.all_languages)
                )
            }
            items(
                items = languages,
                key = { it.hashCode() }
            ) { language ->
                LanguageItem(
                    language = language,
                    onClick = onClick,
                    currentLanguage = currentLanguage,
                    otherLanguage = otherLanguage,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }
}

@Composable
private fun LanguageTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.padding(horizontal = SpeakEasyTheme.dimens.dp16),
        text = text,
        style = SpeakEasyTheme.typography.titleMedium.medium,
        color = SpeakEasyTheme.colors.textPrimary
    )
}

@Composable
private fun LanguageItem(
    language: LanguageUi,
    onClick: (LanguageUi) -> Unit,
    currentLanguage: String,
    otherLanguage: String,
    modifier: Modifier = Modifier
) {
    if (language.name != otherLanguage) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickableNoRipple { onClick(language) }
                .background(
                    if (language.name == currentLanguage) {
                        SpeakEasyTheme.colors.backgroundSecondary
                    } else {
                        SpeakEasyTheme.colors.backgroundModal
                    }
                ).padding(
                    vertical = SpeakEasyTheme.dimens.dp12,
                    horizontal = SpeakEasyTheme.dimens.dp16
                ),
            verticalAlignment = Alignment.CenterVertically

        ) {
            if (language.flag != null) {
                FlagItem(language.flag)
            }
            SpacerWidth(SpeakEasyTheme.dimens.dp12)
            Text(
                modifier = Modifier.weight(1f),
                text = language.name,
                style = SpeakEasyTheme.typography.titleMedium.bold,
                color = SpeakEasyTheme.colors.textPrimary
            )
            if (language.name == currentLanguage) {
                Image(
                    modifier = Modifier.size(SpeakEasyTheme.dimens.dp24),
                    painter = painterResource(Res.drawable.check_icon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = SpeakEasyTheme.colors.iconsPrimary)
                )
            }
        }
    }
}