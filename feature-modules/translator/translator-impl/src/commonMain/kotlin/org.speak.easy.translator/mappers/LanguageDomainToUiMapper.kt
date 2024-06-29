package org.speak.easy.translator.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.domain.models.LanguageDomain
import org.speak.easy.translator.models.LanguageUi
import org.speak.easy.ui.core.models.LanguageWithFlag

internal class LanguageDomainToUiMapper : Mapper<LanguageDomain, LanguageUi> {

    override fun map(from: LanguageDomain): LanguageUi = from.run {
        LanguageUi(
            languageCode = language,
            name = name,
            flag = LanguageWithFlag.find(language)
        )
    }
}