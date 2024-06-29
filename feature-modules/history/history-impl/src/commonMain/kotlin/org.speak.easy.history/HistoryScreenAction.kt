package org.speak.easy.history

import org.speak.easy.history.models.HistoryModel

sealed interface HistoryScreenAction {
    data class OnSearch(val query: String) : HistoryScreenAction
    data class OnMoreIconClick(val item: HistoryModel) : HistoryScreenAction
    data class OnDeleteIcon(val item: HistoryModel) : HistoryScreenAction
}