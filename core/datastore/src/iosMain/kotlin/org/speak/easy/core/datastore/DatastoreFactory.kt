package org.speak.easy.core.datastore

import kotlinx.cinterop.ExperimentalForeignApi
import org.speak.easy.core.PlatformConfiguration
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class DatastoreFactory actual constructor(platformConfiguration: PlatformConfiguration) {

    @OptIn(ExperimentalForeignApi::class)
    actual fun createSelectedLanguageDataStore(): CurrentLanguageDataStore {
        return CurrentLanguageDataStoreImpl {
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