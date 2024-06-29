package org.speak.easy.translator.models

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.DrawableResource

@Immutable
data class LanguageUi(
    val languageCode: String,
    val name: String,
    val flag: DrawableResource?
) {

    companion object {
        val unknown = LanguageUi(
            languageCode = "",
            name = "",
            flag = null
        )
    }
}