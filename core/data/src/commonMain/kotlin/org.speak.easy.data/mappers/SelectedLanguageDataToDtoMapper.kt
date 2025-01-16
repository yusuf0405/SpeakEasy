package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.core.datastore.models.SelectedLanguageDto
import org.speak.easy.data.models.SelectedLanguageData

class SelectedLanguageDataToDtoMapper : Mapper<SelectedLanguageData, SelectedLanguageDto> {
    override fun map(from: SelectedLanguageData): SelectedLanguageDto = from.run {
        SelectedLanguageDto(
            targetLanguage = targetLanguage,
            sourceLanguage = sourceLanguage,
            targetLanguageCode = targetLanguageCode,
            sourceLanguageCode = sourceLanguageCode
        )
    }
}