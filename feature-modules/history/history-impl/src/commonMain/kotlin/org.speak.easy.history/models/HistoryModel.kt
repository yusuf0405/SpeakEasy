package org.speak.easy.history.models

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate

@Immutable
data class HistoryModel(
    val id: Long,
    val targetLanguage: String,
    val sourceLanguage: String,
    val targetText: String,
    val sourceText: String,
    val date: LocalDate
)