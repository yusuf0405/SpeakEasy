package org.speak.easy.core.datastore

import kotlinx.cinterop.ExperimentalForeignApi
import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.core.datastore.preferences.AppPreferencesDataStore
import org.speak.easy.core.datastore.preferences.DefaultAppPreferencesDataStore
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class DatastoreFactory actual constructor(platformConfiguration: PlatformConfiguration) {

    @OptIn(ExperimentalForeignApi::class)
    actual fun createSelectedLanguageDataStore(): CurrentLanguageDataStore {
        return DefaultCurrentLanguageDataStore {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/$currentLanguageDataStoreFileName"
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun createAppPreferencesDataStore(): AppPreferencesDataStore {
        return DefaultAppPreferencesDataStore {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/$appPreferencesDataStoreFileName"
        }
    }
}