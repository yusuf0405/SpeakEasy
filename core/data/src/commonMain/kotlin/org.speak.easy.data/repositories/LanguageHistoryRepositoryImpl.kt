package org.speak.easy.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.speak.easy.data.local.mappers.LanguageHistoryEntityToDataMapper
import org.speak.easy.data.local.source.LanguageHistoryLocalDataSource
import org.speak.easy.data.mappers.LanguageDataToDomainMapper
import org.speak.easy.data.mappers.LanguageDataToEntityMapper
import org.speak.easy.data.mappers.LanguageDomainToDataMapper
import org.speak.easy.domain.LanguageHistoryRepository
import org.speak.easy.domain.models.LanguageDomain

internal class LanguageHistoryRepositoryImpl(
    private val languageHistoryLocalDataSource: LanguageHistoryLocalDataSource,
    private val languageDataToDomainMapper: LanguageDataToDomainMapper,
    private val languageHistoryEntityToDataMapper: LanguageHistoryEntityToDataMapper,
    private val languageDomainToDataMapper: LanguageDomainToDataMapper,
    private val languageDataToEntityMapper: LanguageDataToEntityMapper
) : LanguageHistoryRepository {

    override fun observeLanguageHistory(): Flow<List<LanguageDomain>> {
        return languageHistoryLocalDataSource.observeLanguageHistories()
            .map { it.map(languageHistoryEntityToDataMapper::map) }
            .map { it.map(languageDataToDomainMapper::map) }
    }

    override suspend fun addLanguage(language: LanguageDomain) {
        languageDataToEntityMapper.map(languageDomainToDataMapper.map(language)).apply {
            languageHistoryLocalDataSource.insertOrUpdate(this)
        }
    }
}