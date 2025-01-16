package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.core.datastore.models.SelectedLanguageDto
import org.speak.easy.data.models.SelectedLanguageData

class SelectedLanguageDtoToDataMapper : Mapper<SelectedLanguageDto, SelectedLanguageData> {
    override fun map(from: SelectedLanguageDto): SelectedLanguageData = from.run {
        SelectedLanguageData(
            targetLanguage = targetLanguage,
            sourceLanguage = sourceLanguage,
            targetLanguageCode = targetLanguageCode,
            sourceLanguageCode = sourceLanguageCode
        )
    }
}