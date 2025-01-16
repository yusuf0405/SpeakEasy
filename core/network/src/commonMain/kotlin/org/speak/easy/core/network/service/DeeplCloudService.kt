package org.speak.easy.core.network.service

import org.speak.easy.core.network.models.LanguageCloud
import org.speak.easy.core.network.models.TranslateRequestBodyCloud
import org.speak.easy.core.network.models.TranslationCloud

interface DeeplCloudService {

    suspend fun fetchTranslatorLanguages(type: String): List<LanguageCloud>

    suspend fun translateText(body: TranslateRequestBodyCloud): List<TranslationCloud>
}