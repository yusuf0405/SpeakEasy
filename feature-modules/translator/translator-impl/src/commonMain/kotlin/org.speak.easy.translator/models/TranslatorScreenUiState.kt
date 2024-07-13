package org.speak.easy.translator.models

import androidx.compose.runtime.Immutable
import org.speak.easy.ui.components.models.LanguageUi

internal enum class LastLanguageState {
    FROM_NETWORK,
    FROM_CACHE,
}

@Immutable
internal data class TranslatorScreenUiState(
    val targetLanguage: LanguageUi,
    val sourceLanguage: LanguageUi,
    val targetText: String,
    val sourceText: String,
    val recordingStatus: RecordingStatus,
    val lastLanguageState: LastLanguageState,
    val isLoading: Boolean,
    val isError: Boolean
) {

    companion object {
        val unknown = TranslatorScreenUiState(
            sourceLanguage = LanguageUi.unknown,
            targetLanguage = LanguageUi.unknown,
            sourceText = "",
            targetText = "",
            recordingStatus = RecordingStatus.Stopped,
            lastLanguageState = LastLanguageState.FROM_NETWORK,
            isLoading = false,
            isError = false,
        )
    }
}