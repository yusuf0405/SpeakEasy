package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.core.database.models.LanguageHistoryEntity
import org.speak.easy.data.models.LanguageData

internal class LanguageHistoryEntityToDataMapper : Mapper<LanguageHistoryEntity, LanguageData> {
    override fun map(from: LanguageHistoryEntity): LanguageData = from.run {
        LanguageData(language = languageCode, name = language)
    }
}