package org.speak.easy.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.data.local.data.store.SelectedLanguageDataStore
import org.speak.easy.data.local.data.store.SelectedLanguageDataStoreImpl
import org.speak.easy.data.local.database.TranslationHistoryDatabase
import org.speak.easy.data.local.database.dataBaseFileName
import org.speak.easy.data.local.database.dataStoreFileName
import org.speak.easy.data.local.database.instantiateImpl
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory actual constructor(platformConfiguration: PlatformConfiguration) {

    internal actual fun createRoomDatabase(): TranslationHistoryDatabase {
        val dbFile = "${fileDirectory()}/$dataBaseFileName"
        return Room.databaseBuilder<TranslationHistoryDatabase>(
            name = dbFile,
            factory = { TranslationHistoryDatabase::class.instantiateImpl() }
        ).setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun fileDirectory(): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory).path!!
    }

    @OptIn(ExperimentalForeignApi::class)
    internal actual fun createSelectedLanguageDataStore(): SelectedLanguageDataStore {
        return SelectedLanguageDataStoreImpl {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/$dataStoreFileName"
        }
    }
}