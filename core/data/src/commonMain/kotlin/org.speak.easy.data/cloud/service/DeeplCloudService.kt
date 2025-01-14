package org.speak.easy.data.cloud.service

import org.speak.easy.data.cloud.models.LanguageCloud
import org.speak.easy.data.cloud.models.TranslateRequestBodyCloud
import org.speak.easy.data.cloud.models.TranslationCloud

internal interface DeeplCloudService {

    suspend fun fetchTranslatorLanguages(type: String): List<LanguageCloud>

    suspend fun translateText(body: TranslateRequestBodyCloud): List<TranslationCloud>
}