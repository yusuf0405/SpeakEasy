package org.speak.easy.core.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationCloud(
    @SerialName("detected_source_language")
    val detectedSourceLanguage: String,
    @SerialName("text")
    val text: String
)