package org.speak.easy.core.datastore.models

import kotlinx.serialization.Serializable

@Serializable
data class AppPreferencesDto(
    val theme: ThemeTypeDto
) {

    companion object {
        val default = AppPreferencesDto(
            theme = ThemeTypeDto.SYSTEM
        )
    }
}