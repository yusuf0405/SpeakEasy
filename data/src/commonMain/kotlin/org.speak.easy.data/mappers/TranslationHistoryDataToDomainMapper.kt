package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.models.TranslationHistoryData
import org.speak.easy.domain.models.TranslationHistoryDomain

internal class TranslationHistoryDataToDomainMapper :
    Mapper<TranslationHistoryData, TranslationHistoryDomain> {

    override fun map(from: TranslationHistoryData): TranslationHistoryDomain = from.run {
        TranslationHistoryDomain(
            id = id,
            targetText = targetText,
            targetLanguage = targetLanguage,
            sourceLanguage = sourceLanguage,
            sourceText = sourceText,
            dateInMills = dateInMills
        )
    }
}