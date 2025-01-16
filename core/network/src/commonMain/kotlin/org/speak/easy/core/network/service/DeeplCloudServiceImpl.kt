package org.speak.easy.core.network.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.speak.easy.core.network.models.LanguageCloud
import org.speak.easy.core.network.models.TranslateRequestBodyCloud
import org.speak.easy.core.network.models.TranslationCloud
import org.speak.easy.core.network.models.TranslationsResponse

class DeeplCloudServiceImpl(private val httpClient: HttpClient) : DeeplCloudService {

    override suspend fun fetchTranslatorLanguages(type: String): List<LanguageCloud> {
        return httpClient.get("/v2/languages") {
            url {
                parameters.append("type", type)
            }
        }.body()
    }

    override suspend fun translateText(body: TranslateRequestBodyCloud): List<TranslationCloud> {
        return httpClient.post("/v2/translate") {
            setBody(body.toString())
        }.body<TranslationsResponse>().translations
    }
}