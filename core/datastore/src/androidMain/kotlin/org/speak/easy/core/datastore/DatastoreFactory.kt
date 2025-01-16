package org.speak.easy.core.datastore

import org.speak.easy.core.PlatformConfiguration

actual class DatastoreFactory actual constructor(
    private val platformConfiguration: PlatformConfiguration
) {
    private val app by lazy { platformConfiguration.androidContext }

    actual fun createSelectedLanguageDataStore(): CurrentLanguageDataStore {
        return CurrentLanguageDataStoreImpl {
            app.filesDir.resolve(dataStoreFileName).absolutePath
        }
    }
}