package org.speak.easy.data.cloud.source

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import okio.IOException
import org.speak.easy.core.DispatcherProvider
import org.speak.easy.data.cloud.models.LanguageCloud
import org.speak.easy.data.cloud.models.TranslateRequestBodyCloud
import org.speak.easy.data.cloud.models.TranslationCloud
import org.speak.easy.data.cloud.service.DeeplCloudService

internal class TranslatorCloudDataSourceImpl(
    private val deeplCloudService: DeeplCloudService,
    private val dispatcherProvider: DispatcherProvider
) : TranslatorCloudDataSource {

    override suspend fun fetchLanguages(type: String): List<LanguageCloud> {
        return withContext(dispatcherProvider.io) {
            try {
                deeplCloudService.fetchTranslatorLanguages(type)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to fetch languages from cloud\n${e.message}", e)
            }
        }
    }

    override suspend fun translateText(body: TranslateRequestBodyCloud): List<TranslationCloud> {
        return withContext(dispatcherProvider.io) {
            try {
                deeplCloudService.translateText(body)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to translate text from cloud\n${e.message}", e)
            }
        }
    }
}