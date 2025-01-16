package org.speak.easy.core.datastore

import org.speak.easy.core.PlatformConfiguration

internal const val dataStoreFileName = "latest-selected-language.json"

expect class DatastoreFactory constructor(platformConfiguration: PlatformConfiguration) {
    fun createSelectedLanguageDataStore(): CurrentLanguageDataStore
}