package org.speak.easy.history.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.core.exstensions.toLocalDate
import org.speak.easy.domain.models.TranslationHistoryDomain
import org.speak.easy.history.models.HistoryModel

internal class TranslationHistoryDomainToUiMapper :
    Mapper<TranslationHistoryDomain, HistoryModel> {

    override fun map(from: TranslationHistoryDomain): HistoryModel = from.run {
        HistoryModel(
            id = id,
            targetLanguage = targetLanguage,
            sourceLanguage = sourceLanguage,
            targetText = targetText,
            sourceText = sourceText,
            date = dateInMills.toLocalDate()
        )
    }
}