package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.models.SelectedLanguageData
import org.speak.easy.domain.models.SelectedLanguageDomain

internal class SelectedLanguageDomainToDataMapper :
    Mapper<SelectedLanguageDomain, SelectedLanguageData> {

    override fun map(from: SelectedLanguageDomain): SelectedLanguageData = from.run {
        SelectedLanguageData(
            targetLanguageCode = targetLanguageCode,
            targetLanguage = targetLanguage,
            sourceLanguage = sourceLanguage,
            sourceLanguageCode = sourceLanguageCode
        )
    }
}