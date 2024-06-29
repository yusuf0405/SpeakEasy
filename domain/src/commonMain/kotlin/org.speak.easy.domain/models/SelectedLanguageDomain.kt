package org.speak.easy.domain.models

data class SelectedLanguageDomain(
    val targetLanguage: String,
    val sourceLanguage: String,
    val targetLanguageCode: String,
    val sourceLanguageCode: String,
) {
    companion object {

        val unknown = SelectedLanguageDomain(
            targetLanguage = "",
            sourceLanguage = "",
            targetLanguageCode = "",
            sourceLanguageCode = ""
        )
    }
}