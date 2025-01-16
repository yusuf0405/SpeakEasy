package org.speak.easy.core.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LanguageCloud(
    @SerialName("language")
    val language: String,
    @SerialName("name")
    val name: String
)