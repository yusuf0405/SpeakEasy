package org.speak.easy.data.cloud.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TranslationsResponse(
    @SerialName("translations")
    val translations: List<TranslationCloud>
)