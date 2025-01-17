package org.speak.easy.core.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.speak.easy.core.PlatformConfiguration

actual class DatabaseFactory actual constructor(
    private val platformConfiguration: PlatformConfiguration
) {
    private val app by lazy { platformConfiguration.androidContext }

    actual fun createRoomDatabase(): TranslationHistoryDatabase {
        val dbFile = app.getDatabasePath(dataBaseFileName)
        return Room.databaseBuilder<TranslationHistoryDatabase>(app, dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}