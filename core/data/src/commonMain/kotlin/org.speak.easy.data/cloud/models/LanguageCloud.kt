package org.speak.easy.data.cloud.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LanguageCloud(
    @SerialName("language")
    val language: String,
    @SerialName("name")
    val name: String
)