package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.models.TranslationData
import org.speak.easy.domain.models.TranslationDomain

internal class TranslationDataToDomainMapper : Mapper<TranslationData, TranslationDomain> {

    override fun map(from: TranslationData): TranslationDomain = from.run {
        TranslationDomain(
            detectedSourceLanguage = detectedSourceLanguage,
            text = text
        )
    }
}