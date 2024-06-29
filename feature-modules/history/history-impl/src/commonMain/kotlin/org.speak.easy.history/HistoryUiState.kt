package org.speak.easy.history

import androidx.compose.runtime.Immutable
import org.speak.easy.history.models.HistoryModel

@Immutable
data class HistoryUiState(
    val histories: List<HistoryModel>,
    val searchQuery: String = "",
) {

    companion object {
        val unknown = HistoryUiState(emptyList())
    }
}