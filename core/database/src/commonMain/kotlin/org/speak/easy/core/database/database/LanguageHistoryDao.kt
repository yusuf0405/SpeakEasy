package org.speak.easy.core.database.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.speak.easy.core.database.models.LanguageHistoryEntity

@Dao
interface LanguageHistoryDao {

    @Query("SELECT * FROM language_history")
    fun observeHistories(): Flow<List<LanguageHistoryEntity>>

    @Query("SELECT * FROM language_history")
    suspend fun fetchHistories(): List<LanguageHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: LanguageHistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(items: List<LanguageHistoryEntity>)

    @Delete
    suspend fun deleteHistory(item: LanguageHistoryEntity)

    @Query("DELETE FROM language_history WHERE id = :id")
    suspend fun removeHistoryById(id: Long)

    @Query("DELETE FROM language_history")
    suspend fun clearHistory()
}