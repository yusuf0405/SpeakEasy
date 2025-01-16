package org.speak.easy.core.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslateRequestBodyCloud(
    @SerialName("text")
    private val text: String,
    @SerialName("target_lang")
    private val targetLang: String,
    @SerialName("source_lang")
    private val sourceLang: String,
) {

    override fun toString(): String {
        return "{\"text\":[\"$text\"],\"target_lang\":\"$targetLang\",\"source_lang\":\"$sourceLang\"}"
    }
}