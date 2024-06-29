package org.speak.easy.data.models

data class TranslationHistoryData(
    val id: Long,
    val targetLanguage: String,
    val sourceLanguage: String,
    val targetText: String,
    val sourceText: String,
    val dateInMills: Long
)