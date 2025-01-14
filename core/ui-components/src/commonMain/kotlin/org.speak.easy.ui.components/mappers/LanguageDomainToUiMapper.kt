package org.speak.easy.ui.components.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.domain.models.LanguageDomain
import org.speak.easy.ui.components.models.LanguageUi
import org.speak.easy.core.ui.models.LanguageWithFlag

class LanguageDomainToUiMapper : Mapper<LanguageDomain, LanguageUi> {

    override fun map(from: LanguageDomain): LanguageUi = from.run {
        LanguageUi(
            languageCode = language,
            name = name,
            flag = LanguageWithFlag.find(language)
        )
    }
}