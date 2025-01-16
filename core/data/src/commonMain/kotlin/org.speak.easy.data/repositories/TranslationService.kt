package org.speak.easy.data.repositories

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.speak.easy.core.database.models.TranslationHistoryEntity
import org.speak.easy.core.database.source.TranslationHistoryLocalDataSource
import org.speak.easy.core.network.source.TranslatorCloudDataSource
import org.speak.easy.data.mappers.TranslateRequestBodyDataToCloudMapper
import org.speak.easy.data.mappers.TranslateRequestBodyDomainToDataMapper
import org.speak.easy.data.mappers.TranslationCloudToDataMapper
import org.speak.easy.data.mappers.TranslationDataToDomainMapper
import org.speak.easy.domain.models.TranslateRequestBodyDomain
import org.speak.easy.domain.models.TranslationDomain
import kotlin.random.Random

internal interface TranslationService {
    suspend fun translateText(body: TranslateRequestBodyDomain): List<TranslationDomain>
}

internal class DefaultTranslationService(
    private val cloudDataSource: TranslatorCloudDataSource,
    private val bodyDataToCloudMapper: TranslateRequestBodyDataToCloudMapper,
    private val bodyDomainToDataMapper: TranslateRequestBodyDomainToDataMapper,
    private val cloudToDataMapper: TranslationCloudToDataMapper,
    private val dataToDomainMapper: TranslationDataToDomainMapper,
    private val historyLocalDataSource: TranslationHistoryLocalDataSource
) : TranslationService {

    override suspend fun translateText(body: TranslateRequestBodyDomain): List<TranslationDomain> {
        val bodyCloud = bodyDomainToDataMapper.map(body).run {
            bodyDataToCloudMapper.map(this)
        }
        val translations = cloudDataSource
            .translateText(bodyCloud)
            .map(cloudToDataMapper::map)

        withContext(NonCancellable) {
            val historyItem = TranslationHistoryEntity(
                targetLanguage = body.targetFullLang,
                sourceLanguage = body.sourceFullLang,
                dateInMills = Clock.System.now().toEpochMilliseconds(),
                targetText = translations.first().text,
                sourceText = body.text,
                id = Random.nextLong()
            )
            historyLocalDataSource.insertOrUpdate(historyItem)
        }
        return translations.map(dataToDomainMapper::map)
    }
}