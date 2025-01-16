package org.speak.easy.core.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.speak.easy.core.network.models.TranslationCloud

@Serializable
internal data class TranslationsResponse(
    @SerialName("translations")
    val translations: List<TranslationCloud>
)