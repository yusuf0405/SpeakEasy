package org.speak.easy.core.network.source

import org.speak.easy.core.network.models.LanguageCloud
import org.speak.easy.core.network.models.TranslateRequestBodyCloud
import org.speak.easy.core.network.models.TranslationCloud

interface TranslatorCloudDataSource {

    suspend fun fetchLanguages(type: String): List<LanguageCloud>

    suspend fun translateText(body: TranslateRequestBodyCloud): List<TranslationCloud>

}