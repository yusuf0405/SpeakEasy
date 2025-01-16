package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.core.database.models.TranslationHistoryEntity
import org.speak.easy.data.models.TranslationHistoryData

internal class TranslationHistoryItemToDataMapper :
    Mapper<TranslationHistoryEntity, TranslationHistoryData> {

    override fun map(from: TranslationHistoryEntity): TranslationHistoryData = from.run {
        TranslationHistoryData(
            id = id,
            targetText = targetText,
            targetLanguage = targetLanguage,
            sourceLanguage = sourceLanguage,
            sourceText = sourceText,
            dateInMills = dateInMills
        )
    }
}