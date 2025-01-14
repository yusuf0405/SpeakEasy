package org.speak.easy.data.models

internal data class TranslateRequestBodyData(
    val text: String,
    val targetLang: String,
    val sourceLang: String,
    val targetFullLang: String,
    val sourceFullLang: String,
)