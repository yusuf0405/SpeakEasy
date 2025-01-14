package org.speak.easy.domain.models

data class TranslateRequestBodyDomain(
    val text: String,
    val targetLang: String,
    val sourceLang: String,
    val targetFullLang: String,
    val sourceFullLang: String,
)