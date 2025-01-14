package org.speak.easy.ui.components.models

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.DrawableResource
import org.speak.easy.core.ui.models.LanguageWithFlag

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

        val preview = LanguageUi(
            languageCode = "en",
            name = "England",
            flag = LanguageWithFlag.ENGLAND.flagRes
        )
    }
}