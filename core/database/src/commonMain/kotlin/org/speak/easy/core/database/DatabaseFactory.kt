package org.speak.easy.core.database

import org.speak.easy.core.PlatformConfiguration

expect class DatabaseFactory constructor(platformConfiguration: PlatformConfiguration) {
    fun createRoomDatabase(): TranslationHistoryDatabase
}