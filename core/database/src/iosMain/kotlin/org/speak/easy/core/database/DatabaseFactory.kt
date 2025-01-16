package org.speak.easy.core.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.core.database.database.TranslationHistoryDatabase
import org.speak.easy.core.database.database.dataBaseFileName
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory actual constructor(platformConfiguration: PlatformConfiguration) {

    actual fun createRoomDatabase(): TranslationHistoryDatabase {
        val dbFile = "${fileDirectory()}/$dataBaseFileName"
        return Room.databaseBuilder<TranslationHistoryDatabase>(
            name = dbFile,
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
}