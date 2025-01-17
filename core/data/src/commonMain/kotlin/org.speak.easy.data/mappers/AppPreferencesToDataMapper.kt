package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.models.AppPreferencesData
import org.speak.easy.data.models.ThemeTypeData
import org.speak.easy.domain.models.AppPreferences
import org.speak.easy.domain.models.ThemeType

class AppPreferencesToDataMapper : Mapper<AppPreferences, AppPreferencesData> {
    override fun map(from: AppPreferences): AppPreferencesData = from.run {
        AppPreferencesData(
            theme = when (theme) {
                ThemeType.SYSTEM -> ThemeTypeData.SYSTEM
                ThemeType.DARK -> ThemeTypeData.DARK
                ThemeType.LIGHT -> ThemeTypeData.LIGHT
            }
        )
    }
}