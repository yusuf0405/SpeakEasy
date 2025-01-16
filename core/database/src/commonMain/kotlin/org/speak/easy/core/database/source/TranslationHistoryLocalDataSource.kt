package org.speak.easy.core.database.source

import kotlinx.coroutines.flow.Flow
import org.speak.easy.core.database.models.TranslationHistoryEntity

interface TranslationHistoryLocalDataSource {

    fun observeTranslationHistories(): Flow<List<TranslationHistoryEntity>>

    suspend fun fetchTranslationHistories(): List<TranslationHistoryEntity>

    suspend fun insertOrUpdate(item: TranslationHistoryEntity)

    suspend fun insertOrUpdate(items: List<TranslationHistoryEntity>)

    suspend fun deleteHistory(item: TranslationHistoryEntity)

    suspend fun removeHistoryById(id: Long)

    suspend fun clearHistory()
}