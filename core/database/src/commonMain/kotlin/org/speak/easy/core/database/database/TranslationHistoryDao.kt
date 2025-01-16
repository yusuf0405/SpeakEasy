package org.speak.easy.core.database.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.speak.easy.core.database.models.TranslationHistoryEntity

@Dao
interface TranslationHistoryDao {

    @Query("SELECT * FROM translations_history")
    fun observeTranslationHistories(): Flow<List<TranslationHistoryEntity>>

    @Query("SELECT * FROM translations_history")
    suspend fun fetchTranslationHistories(): List<TranslationHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: TranslationHistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(items: List<TranslationHistoryEntity>)

    @Delete
    suspend fun deleteHistory(item: TranslationHistoryEntity)

    @Query("DELETE FROM translations_history WHERE id = :id")
    suspend fun removeHistoryById(id: Long)

    @Query("DELETE FROM translations_history")
    suspend fun clearHistory()
}