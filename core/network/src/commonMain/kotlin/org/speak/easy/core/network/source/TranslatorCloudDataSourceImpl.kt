package org.speak.easy.core.network.source

import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.speak.easy.core.DispatcherProvider
import org.speak.easy.core.network.models.LanguageCloud
import org.speak.easy.core.network.models.TranslateRequestBodyCloud
import org.speak.easy.core.network.models.TranslationCloud
import org.speak.easy.core.network.service.DeeplCloudService

class TranslatorCloudDataSourceImpl(
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