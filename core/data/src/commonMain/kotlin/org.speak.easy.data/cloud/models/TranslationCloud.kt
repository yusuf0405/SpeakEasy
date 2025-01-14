package org.speak.easy.data.cloud.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TranslationCloud(
    @SerialName("detected_source_language")
    val detectedSourceLanguage: String,
    @SerialName("text")
    val text: String
)