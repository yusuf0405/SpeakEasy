package org.speak.easy.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.data.local.data.store.SelectedLanguageDataStore
import org.speak.easy.data.local.data.store.SelectedLanguageDataStoreImpl
import org.speak.easy.data.local.database.TranslationHistoryDatabase
import org.speak.easy.data.local.database.dataBaseFileName
import org.speak.easy.data.local.database.dataStoreFileName

actual class DatabaseFactory actual constructor(
    private val platformConfiguration: PlatformConfiguration
) {
    private val app by lazy { platformConfiguration.androidContext }

    internal actual fun createRoomDatabase(): TranslationHistoryDatabase {
        val dbFile = app.getDatabasePath(dataBaseFileName)
        return Room.databaseBuilder<TranslationHistoryDatabase>(app, dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    internal actual fun createSelectedLanguageDataStore(): SelectedLanguageDataStore {
        return SelectedLanguageDataStoreImpl {
            app.filesDir.resolve(dataStoreFileName).absolutePath
        }
    }
}
