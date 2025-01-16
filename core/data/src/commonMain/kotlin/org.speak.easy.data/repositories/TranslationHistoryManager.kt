package org.speak.easy.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.speak.easy.core.database.source.TranslationHistoryLocalDataSource
import org.speak.easy.data.mappers.TranslationHistoryDataToDomainMapper
import org.speak.easy.data.mappers.TranslationHistoryItemToDataMapper
import org.speak.easy.domain.models.TranslationHistoryDomain

internal interface TranslationHistoryManager {
    fun observeTranslationHistory(): Flow<List<TranslationHistoryDomain>>
    suspend fun removeHistoryById(id: Long)
    suspend fun clearHistory()
}

internal class DefaultTranslationHistoryManager(
    private val localDataSource: TranslationHistoryLocalDataSource,
    private val historyItemToDataMapper: TranslationHistoryItemToDataMapper,
    private val historyDataToDomainMapper: TranslationHistoryDataToDomainMapper
) : TranslationHistoryManager {

    override fun observeTranslationHistory(): Flow<List<TranslationHistoryDomain>> {
        return localDataSource.observeTranslationHistories().map { translations ->
            translations
                .map(historyItemToDataMapper::map)
                .map(historyDataToDomainMapper::map)
                .sortedByDescending { it.dateInMills }
        }
    }

    override suspend fun removeHistoryById(id: Long) {
        localDataSource.removeHistoryById(id)
    }

    override suspend fun clearHistory() {
        localDataSource.clearHistory()
    }
}