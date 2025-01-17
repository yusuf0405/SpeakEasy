package org.speak.easy.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AppPreferencesData(
    val theme: ThemeTypeData
) {

    companion object {
        val default = AppPreferencesData(
            theme = ThemeTypeData.SYSTEM
        )
    }
}