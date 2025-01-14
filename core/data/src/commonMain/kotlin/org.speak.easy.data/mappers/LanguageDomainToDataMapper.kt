package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.models.LanguageData
import org.speak.easy.domain.models.LanguageDomain

internal class LanguageDomainToDataMapper : Mapper<LanguageDomain, LanguageData> {
    override fun map(from: LanguageDomain): LanguageData = from.run {
        LanguageData(language = language, name = name)
    }
}