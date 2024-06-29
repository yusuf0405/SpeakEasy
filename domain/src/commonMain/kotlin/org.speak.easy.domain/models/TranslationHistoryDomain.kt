package org.speak.easy.domain.models

data class TranslationHistoryDomain(
    val id: Long,
    val targetLanguage: String,
    val sourceLanguage: String,
    val targetText: String,
    val sourceText: String,
    val dateInMills: Long
)