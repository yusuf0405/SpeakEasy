package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.core.datastore.models.AppPreferencesDto
import org.speak.easy.core.datastore.models.ThemeTypeDto
import org.speak.easy.data.models.AppPreferencesData
import org.speak.easy.data.models.ThemeTypeData

class AppPreferencesDtoToDataMapper : Mapper<AppPreferencesDto, AppPreferencesData> {
    override fun map(from: AppPreferencesDto): AppPreferencesData = from.run {
        AppPreferencesData(
            theme = when (theme) {
                ThemeTypeDto.SYSTEM -> ThemeTypeData.SYSTEM
                ThemeTypeDto.DARK -> ThemeTypeData.DARK
                ThemeTypeDto.LIGHT -> ThemeTypeData.LIGHT
            }
        )
    }
}