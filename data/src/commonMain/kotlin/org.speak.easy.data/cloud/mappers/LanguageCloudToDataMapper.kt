package org.speak.easy.data.cloud.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.cloud.models.LanguageCloud
import org.speak.easy.data.models.LanguageData

internal class LanguageCloudToDataMapper : Mapper<LanguageCloud, LanguageData> {

    override fun map(from: LanguageCloud): LanguageData = from.run {
        LanguageData(language = language, name = name)
    }
}