package org.speak.easy.languages

import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject
import org.speak.easy.languages.api.LanguageFeatureApi
import org.speak.easy.ui.components.models.LanguageUi

object LanguageFeature : LanguageFeatureApi {

    @Composable
    override fun showLanguagesModal(
        isSourceLanguage: Boolean,
        currentLanguage: String,
        otherLanguage: String,
        onClick: (LanguageUi) -> Unit,
        onDismissRequest: () -> Unit
    ) {
        val viewModel = koinInject<LanguagesViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

        LanguagesBottomSheet(
            modifier = getPlatformModifier().modifier,
            uiState = uiState,
            languages = if (isSourceLanguage) uiState.sourceLanguages else uiState.targetLanguages,
            currentLanguage = currentLanguage,
            otherLanguage = otherLanguage,
            bottomSheetState = bottomSheetState,
            onClick = onClick,
            onAction = viewModel::onAction,
            onDismissRequest = {
                onDismissRequest()
                viewModel.onAction(LanguageScreenAction.OnClearSearchQuery)
            }
        )
    }
}