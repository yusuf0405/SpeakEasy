package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.models.LanguageData
import org.speak.easy.domain.models.LanguageDomain

internal class LanguageDataToDomainMapper : Mapper<LanguageData, LanguageDomain> {

    override fun map(from: LanguageData): LanguageDomain = from.run {
        LanguageDomain(language = language, name = name)
    }
}