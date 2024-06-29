package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.local.models.LanguageHistoryEntity
import org.speak.easy.data.models.LanguageData
import kotlin.random.Random

internal class LanguageDataToEntityMapper : Mapper<LanguageData, LanguageHistoryEntity> {
    override fun map(from: LanguageData): LanguageHistoryEntity = from.run {
        LanguageHistoryEntity(
            language = name,
            languageCode = language,
            id = Random.nextLong()
        )
    }
}