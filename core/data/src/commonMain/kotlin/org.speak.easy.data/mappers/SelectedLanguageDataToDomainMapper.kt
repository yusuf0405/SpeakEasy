package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.models.SelectedLanguageData
import org.speak.easy.domain.models.SelectedLanguageDomain

internal class SelectedLanguageDataToDomainMapper :
    Mapper<SelectedLanguageData, SelectedLanguageDomain> {

    override fun map(from: SelectedLanguageData): SelectedLanguageDomain = from.run {
        SelectedLanguageDomain(
            targetLanguageCode = targetLanguageCode,
            targetLanguage = targetLanguage,
            sourceLanguage = sourceLanguage,
            sourceLanguageCode = sourceLanguageCode
        )
    }
}