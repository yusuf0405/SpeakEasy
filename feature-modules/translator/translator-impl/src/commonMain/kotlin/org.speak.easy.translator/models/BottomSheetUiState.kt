package org.speak.easy.translator.models

import androidx.compose.runtime.Immutable

@Immutable
internal data class BottomSheetUiState(
    val sourceLanguages: List<LanguageUi>,
    val targetLanguages: List<LanguageUi>,
    val historyLanguages: List<LanguageUi>,
    val searchQuery: String,
    val isLoading: Boolean,
    val isError: Boolean,
) {

    companion object {
        val unknown = BottomSheetUiState(
            sourceLanguages = emptyList(),
            targetLanguages = emptyList(),
            historyLanguages = emptyList(),
            searchQuery = "",
            isLoading = false,
            isError = false
        )
    }
}