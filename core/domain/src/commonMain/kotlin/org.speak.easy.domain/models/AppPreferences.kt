package org.speak.easy.domain.models

import kotlinx.serialization.Serializable

enum class ThemeType {
    LIGHT,
    DARK,
    SYSTEM
}

@Serializable
data class AppPreferences(
    val theme: ThemeType
) {

    companion object {
        val default = AppPreferences(
            theme = ThemeType.SYSTEM
        )
    }
}