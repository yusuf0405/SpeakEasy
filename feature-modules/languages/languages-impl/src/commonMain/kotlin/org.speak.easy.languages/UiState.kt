package org.speak.easy.languages

import androidx.compose.runtime.Immutable
import org.speak.easy.ui.components.models.LanguageUi

@Immutable
internal data class UiState(
    val sourceLanguages: List<LanguageUi>,
    val targetLanguages: List<LanguageUi>,
    val historyLanguages: List<LanguageUi>,
    val searchQuery: String,
    val isLoading: Boolean,
    val isError: Boolean,
) {

    companion object {
        val unknown = UiState(
            sourceLanguages = emptyList(),
            targetLanguages = emptyList(),
            historyLanguages = emptyList(),
            searchQuery = "",
            isLoading = false,
            isError = false
        )
    }
}