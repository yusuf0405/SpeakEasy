package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.cloud.models.TranslateRequestBodyCloud
import org.speak.easy.data.models.TranslateRequestBodyData

internal class TranslateRequestBodyDataToCloudMapper : Mapper<TranslateRequestBodyData, TranslateRequestBodyCloud> {

    override fun map(from: TranslateRequestBodyData): TranslateRequestBodyCloud = from.run {
        TranslateRequestBodyCloud(
            text = text,
            targetLang = targetLang,
            sourceLang = sourceLang
        )
    }
}