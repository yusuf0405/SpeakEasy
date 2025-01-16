package org.speak.easy.core.datastore.models

import kotlinx.serialization.Serializable

@Serializable
data class SelectedLanguageDto(
    val targetLanguage: String,
    val sourceLanguage: String,
    val targetLanguageCode: String,
    val sourceLanguageCode: String,
) {
    companion object {
        val unknown = SelectedLanguageDto(
            targetLanguage = "",
            sourceLanguage = "",
            targetLanguageCode = "",
            sourceLanguageCode = ""
        )
    }
}