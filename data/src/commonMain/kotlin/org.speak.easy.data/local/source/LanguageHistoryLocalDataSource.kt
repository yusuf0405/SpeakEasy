package org.speak.easy.data.local.source

import kotlinx.coroutines.flow.Flow
import org.speak.easy.data.local.models.LanguageHistoryEntity

internal interface LanguageHistoryLocalDataSource {

    fun observeLanguageHistories(): Flow<List<LanguageHistoryEntity>>

    suspend fun fetchLanguageHistories(): List<LanguageHistoryEntity>

    suspend fun insertOrUpdate(item: LanguageHistoryEntity)

    suspend fun insertOrUpdate(items: List<LanguageHistoryEntity>)

    suspend fun deleteHistory(item: LanguageHistoryEntity)

    suspend fun removeHistoryById(id: Long)

    suspend fun clearHistory()
}