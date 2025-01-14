package org.speak.easy.data.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
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
@ConstructedBy(TranslationDatabaseConstructor::class)
internal abstract class TranslationHistoryDatabase : RoomDatabase() {
    abstract fun getTranslationHistoryDao(): TranslationHistoryDao
    abstract fun getLanguageHistoryDao(): LanguageHistoryDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object TranslationDatabaseConstructor :
    RoomDatabaseConstructor<TranslationHistoryDatabase> {
    override fun initialize(): TranslationHistoryDatabase
}