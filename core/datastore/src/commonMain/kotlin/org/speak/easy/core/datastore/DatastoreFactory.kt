package org.speak.easy.core.datastore

import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.core.datastore.preferences.AppPreferencesDataStore

internal const val currentLanguageDataStoreFileName = "latest-selected-language.json"
internal const val appPreferencesDataStoreFileName = "application-preferences.json"

expect class DatastoreFactory constructor(platformConfiguration: PlatformConfiguration) {
    fun createSelectedLanguageDataStore(): CurrentLanguageDataStore
    fun createAppPreferencesDataStore(): AppPreferencesDataStore
}