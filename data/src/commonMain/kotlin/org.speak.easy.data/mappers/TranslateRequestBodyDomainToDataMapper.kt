package org.speak.easy.data.mappers

import org.speak.easy.core.Mapper
import org.speak.easy.data.models.TranslateRequestBodyData
import org.speak.easy.domain.models.TranslateRequestBodyDomain

internal class TranslateRequestBodyDomainToDataMapper :
    Mapper<TranslateRequestBodyDomain, TranslateRequestBodyData> {

    override fun map(from: TranslateRequestBodyDomain): TranslateRequestBodyData = from.run {
        TranslateRequestBodyData(
            text = text,
            targetLang = targetLang,
            sourceLang = sourceLang,
            targetFullLang = targetFullLang,
            sourceFullLang = sourceFullLang
        )
    }
}