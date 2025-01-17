package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.core.datastore.models.AppPreferencesDto
import org.speak.easy.core.datastore.models.ThemeTypeDto
import org.speak.easy.data.models.AppPreferencesData
import org.speak.easy.data.models.ThemeTypeData

class AppPreferencesDataToDtoMapper : Mapper<AppPreferencesData, AppPreferencesDto> {
    override fun map(from: AppPreferencesData): AppPreferencesDto = from.run {
        AppPreferencesDto(
            theme = when (theme) {
                ThemeTypeData.SYSTEM -> ThemeTypeDto.SYSTEM
                ThemeTypeData.DARK -> ThemeTypeDto.DARK
                ThemeTypeData.LIGHT -> ThemeTypeDto.LIGHT
            }
        )
    }
}