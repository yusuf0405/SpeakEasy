package org.speak.easy.data.cloud.source

import org.speak.easy.data.cloud.models.LanguageCloud
import org.speak.easy.data.cloud.models.TranslateRequestBodyCloud
import org.speak.easy.data.cloud.models.TranslationCloud

internal interface TranslatorCloudDataSource {

    suspend fun fetchLanguages(type: String): List<LanguageCloud>

    suspend fun translateText(body: TranslateRequestBodyCloud): List<TranslationCloud>

}