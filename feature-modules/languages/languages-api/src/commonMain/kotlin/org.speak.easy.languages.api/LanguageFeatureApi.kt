package org.speak.easy.languages.api

import androidx.compose.runtime.Composable
import org.speak.easy.ui.components.models.LanguageUi

interface LanguageFeatureApi {

    @Composable
    fun showLanguagesModal(
        isSourceLanguage: Boolean,
        currentLanguage: String,
        otherLanguage: String,
        onClick: (LanguageUi) -> Unit,
        onDismissRequest: () -> Unit
    )
}