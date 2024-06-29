package org.speak.easy.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.speak.easy.data.local.models.LanguageHistoryEntity
import org.speak.easy.data.local.models.TranslationHistoryEntity

internal const val dataBaseFileName = "speak-easy.db"
internal const val dataStoreFileName = "latest-selected-language.json"

@Database(
    entities = [
        TranslationHistoryEntity::class,
        LanguageHistoryEntity::class,
    ],
    version = 2
)
internal abstract class TranslationHistoryDatabase : RoomDatabase() {
    abstract fun getTranslationHistoryDao(): TranslationHistoryDao
    abstract fun getLanguageHistoryDao(): LanguageHistoryDao
}