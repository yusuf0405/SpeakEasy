package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.models.AppPreferencesData
import org.speak.easy.data.models.ThemeTypeData
import org.speak.easy.domain.models.AppPreferences
import org.speak.easy.domain.models.ThemeType

class AppPreferencesDataDomainToMapper : Mapper<AppPreferencesData, AppPreferences> {
    override fun map(from: AppPreferencesData): AppPreferences = from.run {
        AppPreferences(
            theme = when (theme) {
                ThemeTypeData.SYSTEM -> ThemeType.SYSTEM
                ThemeTypeData.DARK -> ThemeType.DARK
                ThemeTypeData.LIGHT -> ThemeType.LIGHT
            }
        )
    }
}