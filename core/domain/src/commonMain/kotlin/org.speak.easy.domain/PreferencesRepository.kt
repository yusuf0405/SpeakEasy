package org.speak.easy.domain

import kotlinx.coroutines.flow.Flow
import org.speak.easy.domain.models.AppPreferences

interface PreferencesRepository {
    suspend fun setPreferences(preferences: AppPreferences)
    fun observePreferences(): Flow<AppPreferences>
    suspend fun getCurrentPreferences(): AppPreferences
}
