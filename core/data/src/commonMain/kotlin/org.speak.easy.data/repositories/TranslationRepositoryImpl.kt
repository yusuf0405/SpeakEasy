package org.speak.easy.data.repositories

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.speak.easy.data.cloud.mappers.LanguageCloudToDataMapper
import org.speak.easy.data.cloud.mappers.TranslationCloudToDataMapper
import org.speak.easy.data.cloud.source.TranslatorCloudDataSource
import org.speak.easy.data.local.data.store.SelectedLanguageDataStore
import org.speak.easy.data.local.mappers.TranslationHistoryItemToDataMapper
import org.speak.easy.data.local.models.TranslationHistoryEntity
import org.speak.easy.data.local.source.TranslationHistoryLocalDataSource
import org.speak.easy.data.mappers.LanguageDataToDomainMapper
import org.speak.easy.data.mappers.SelectedLanguageDataToDomainMapper
import org.speak.easy.data.mappers.SelectedLanguageDomainToDataMapper
import org.speak.easy.data.mappers.TranslateRequestBodyDataToCloudMapper
import org.speak.easy.data.mappers.TranslateRequestBodyDomainToDataMapper
import org.speak.easy.data.mappers.TranslationDataToDomainMapper
import org.speak.easy.data.mappers.TranslationHistoryDataToDomainMapper
import org.speak.easy.domain.TranslationRepository
import org.speak.easy.domain.models.LanguageDomain
import org.speak.easy.domain.models.LanguageType
import org.speak.easy.domain.models.SelectedLanguageDomain
import org.speak.easy.domain.models.TranslateRequestBodyDomain
import org.speak.easy.domain.models.TranslationDomain
import org.speak.easy.domain.models.TranslationHistoryDomain
import kotlin.random.Random

internal class TranslationRepositoryImpl(
    private val cloudDataSource: TranslatorCloudDataSource,
    private val historyLocalDataSource: TranslationHistoryLocalDataSource,
    private val selectedLanguageDataStore: SelectedLanguageDataStore,
    private val translateRequestBodyDataToCloudMapper: TranslateRequestBodyDataToCloudMapper,
    private val translateRequestBodyDomainToDataMapper: TranslateRequestBodyDomainToDataMapper,
    private val languageCloudToDataMapper: LanguageCloudToDataMapper,
    private val translationCloudToDataMapper: TranslationCloudToDataMapper,
    private val translationDataToDomainMapper: TranslationDataToDomainMapper,
    private val languageDataToDomainMapper: LanguageDataToDomainMapper,
    private val translationHistoryItemToDataMapper: TranslationHistoryItemToDataMapper,
    private val translationHistoryDataToDomainMapper: TranslationHistoryDataToDomainMapper,
    private val selectedLanguageDataToDomainMapper: SelectedLanguageDataToDomainMapper,
    private val selectedLanguageDomainToDataMapper: SelectedLanguageDomainToDataMapper,
) : TranslationRepository {

    override fun observeTranslationHistory(): Flow<List<TranslationHistoryDomain>> {
        return historyLocalDataSource.observeTranslationHistories().map { translations ->
            translations
                .map(translationHistoryItemToDataMapper::map)
                .map(translationHistoryDataToDomainMapper::map)
                .sortedByDescending { it.dateInMills }
        }
    }

    override suspend fun translateText(body: TranslateRequestBodyDomain): List<TranslationDomain> {
        val bodyCloud = translateRequestBodyDomainToDataMapper.map(body).run {
            translateRequestBodyDataToCloudMapper.map(this)
        }
        val translations = cloudDataSource
            .translateText(bodyCloud)
            .map(translationCloudToDataMapper::map)

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

        return translations.map(translationDataToDomainMapper::map)
    }

    override suspend fun fetchLanguages(type: LanguageType): List<LanguageDomain> {
        return cloudDataSource.fetchLanguages(type.name)
            .map(languageCloudToDataMapper::map)
            .map(languageDataToDomainMapper::map)
    }

    override suspend fun updateSelectedLanguage(selectedLanguage: SelectedLanguageDomain) {
        return selectedLanguageDataStore.updateSelectedLanguage(
            selectedLanguageDomainToDataMapper.map(
                selectedLanguage
            )
        )
    }

    override suspend fun fetchLatestSelectedLanguage(): SelectedLanguageDomain {
        return selectedLanguageDataStore
            .getSelectedLanguageData()
            .run(selectedLanguageDataToDomainMapper::map)
    }

    override fun observeSelectedLanguageData(): Flow<SelectedLanguageDomain> {
        return selectedLanguageDataStore
            .observeSelectedLanguageData()
            .map(selectedLanguageDataToDomainMapper::map)
    }

    override suspend fun removeHistoryById(id: Long) {
        historyLocalDataSource.removeHistoryById(id)
    }

    override suspend fun clearHistory() {
        historyLocalDataSource.clearHistory()
    }
}