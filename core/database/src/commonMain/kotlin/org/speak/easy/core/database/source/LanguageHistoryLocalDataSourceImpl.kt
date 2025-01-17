package org.speak.easy.core.database.source

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okio.IOException
import org.speak.easy.core.DispatcherProvider
import org.speak.easy.core.database.LanguageHistoryDao
import org.speak.easy.core.database.models.LanguageHistoryEntity

class LanguageHistoryLocalDataSourceImpl(
    private val languageHistoryDao: LanguageHistoryDao,
    private val dispatcherProvider: DispatcherProvider
) : LanguageHistoryLocalDataSource {

    override fun observeLanguageHistories(): Flow<List<LanguageHistoryEntity>> {
        return languageHistoryDao
            .observeHistories()
            .flowOn(dispatcherProvider.io)
            .catch { e: Throwable ->
                throw IOException("Failed to observe languages histories", e)
            }
    }

    override suspend fun fetchLanguageHistories(): List<LanguageHistoryEntity> {
        return withContext(dispatcherProvider.io) {
            try {
                languageHistoryDao.fetchHistories()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to fetch languages histories", e)
            }
        }
    }

    override suspend fun insertOrUpdate(item: LanguageHistoryEntity) {
        return withContext(dispatcherProvider.io) {
            try {
                languageHistoryDao.insertOrUpdate(item)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to insert or update languages history", e)
            }
        }
    }

    override suspend fun insertOrUpdate(items: List<LanguageHistoryEntity>) {
        return withContext(dispatcherProvider.io) {
            try {
                languageHistoryDao.insertOrUpdate(items)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to insert or update languages histories", e)
            }
        }
    }

    override suspend fun deleteHistory(item: LanguageHistoryEntity) {
        return withContext(dispatcherProvider.io) {
            try {
                languageHistoryDao.deleteHistory(item)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to delete languages history", e)
            }
        }
    }

    override suspend fun removeHistoryById(id: Long) {
        return withContext(dispatcherProvider.io) {
            try {
                languageHistoryDao.removeHistoryById(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to delete languages history", e)
            }
        }
    }

    override suspend fun clearHistory() {
        return withContext(dispatcherProvider.io) {
            try {
                languageHistoryDao.clearHistory()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to clear languages history", e)
            }
        }
    }
}