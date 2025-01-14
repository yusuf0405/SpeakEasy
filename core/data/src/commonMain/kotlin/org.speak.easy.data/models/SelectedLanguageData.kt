package org.speak.easy.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SelectedLanguageData(
    val targetLanguage: String,
    val sourceLanguage: String,
    val targetLanguageCode: String,
    val sourceLanguageCode: String,
) {
    companion object {

        val unknown = SelectedLanguageData(
            targetLanguage = "",
            sourceLanguage = "",
            targetLanguageCode = "",
            sourceLanguageCode = ""
        )
    }
}