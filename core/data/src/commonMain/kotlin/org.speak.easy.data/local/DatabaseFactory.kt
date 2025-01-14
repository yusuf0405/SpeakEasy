package org.speak.easy.data.local

import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.data.local.data.store.SelectedLanguageDataStore
import org.speak.easy.data.local.database.TranslationHistoryDatabase

expect class DatabaseFactory constructor(platformConfiguration: PlatformConfiguration) {
    internal fun createRoomDatabase(): TranslationHistoryDatabase
    internal fun createSelectedLanguageDataStore(): SelectedLanguageDataStore
}