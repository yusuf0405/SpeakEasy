package org.speak.easy.core.database.source

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okio.IOException
import org.speak.easy.core.DispatcherProvider
import org.speak.easy.core.database.database.TranslationHistoryDao
import org.speak.easy.core.database.models.TranslationHistoryEntity

class TranslationHistoryLocalDataSourceImpl(
    private val translationHistoryDao: TranslationHistoryDao,
    private val dispatcherProvider: DispatcherProvider
) : TranslationHistoryLocalDataSource {

    override fun observeTranslationHistories(): Flow<List<TranslationHistoryEntity>> {
        return translationHistoryDao
            .observeTranslationHistories()
            .flowOn(dispatcherProvider.io)
            .catch { e: Throwable ->
                throw IOException("Failed to observe translation histories", e)
            }
    }

    override suspend fun fetchTranslationHistories(): List<TranslationHistoryEntity> {
        return withContext(dispatcherProvider.io) {
            try {
                translationHistoryDao.fetchTranslationHistories()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to fetch translation histories", e)
            }
        }
    }

    override suspend fun insertOrUpdate(item: TranslationHistoryEntity) {
        return withContext(dispatcherProvider.io) {
            try {
                translationHistoryDao.insertOrUpdate(item)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to insert or update translation history", e)
            }
        }
    }

    override suspend fun insertOrUpdate(items: List<TranslationHistoryEntity>) {
        return withContext(dispatcherProvider.io) {
            try {
                translationHistoryDao.insertOrUpdate(items)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to insert or update translation histories", e)
            }
        }
    }

    override suspend fun deleteHistory(item: TranslationHistoryEntity) {
        return withContext(dispatcherProvider.io) {
            try {
                translationHistoryDao.deleteHistory(item)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to delete translation history", e)
            }
        }
    }

    override suspend fun removeHistoryById(id: Long) {
        return withContext(dispatcherProvider.io) {
            try {
                translationHistoryDao.removeHistoryById(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to delete translation history", e)
            }
        }
    }

    override suspend fun clearHistory() {
        return withContext(dispatcherProvider.io) {
            try {
                translationHistoryDao.clearHistory()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to clear history", e)
            }
        }
    }
}