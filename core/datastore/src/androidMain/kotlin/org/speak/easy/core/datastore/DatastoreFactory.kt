package org.speak.easy.core.datastore

import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.core.datastore.preferences.AppPreferencesDataStore
import org.speak.easy.core.datastore.preferences.DefaultAppPreferencesDataStore

actual class DatastoreFactory actual constructor(
    private val platformConfiguration: PlatformConfiguration
) {
    private val app by lazy { platformConfiguration.androidContext }

    actual fun createSelectedLanguageDataStore(): CurrentLanguageDataStore {
        return DefaultCurrentLanguageDataStore {
            app.filesDir.resolve(currentLanguageDataStoreFileName).absolutePath
        }
    }

    actual fun createAppPreferencesDataStore(): AppPreferencesDataStore {
        return DefaultAppPreferencesDataStore {
            app.filesDir.resolve(appPreferencesDataStoreFileName).absolutePath
        }
    }
}