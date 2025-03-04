package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.core.network.models.TranslationCloud
import org.speak.easy.data.models.TranslationData

internal class TranslationCloudToDataMapper : Mapper<TranslationCloud, TranslationData> {

    override fun map(from: TranslationCloud): TranslationData = from.run {
        TranslationData(
            detectedSourceLanguage = detectedSourceLanguage,
            text = text
        )
    }
}