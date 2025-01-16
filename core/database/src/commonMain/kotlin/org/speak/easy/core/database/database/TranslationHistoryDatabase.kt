package org.speak.easy.core.database.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.speak.easy.core.database.models.LanguageHistoryEntity
import org.speak.easy.core.database.models.TranslationHistoryEntity

internal const val dataBaseFileName = "speak-easy.db"

@Database(
    entities = [
        TranslationHistoryEntity::class,
        LanguageHistoryEntity::class,
    ],
    version = 2
)
@ConstructedBy(TranslationDatabaseConstructor::class)
abstract class TranslationHistoryDatabase : RoomDatabase() {
    abstract fun getTranslationHistoryDao(): TranslationHistoryDao
    abstract fun getLanguageHistoryDao(): LanguageHistoryDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object TranslationDatabaseConstructor :
    RoomDatabaseConstructor<TranslationHistoryDatabase> {
    override fun initialize(): TranslationHistoryDatabase
}