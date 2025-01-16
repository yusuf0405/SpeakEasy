package org.speak.easy.core.database

import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.core.database.database.TranslationHistoryDatabase

expect class DatabaseFactory constructor(platformConfiguration: PlatformConfiguration) {
    fun createRoomDatabase(): TranslationHistoryDatabase
}