package org.speak.easy.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.speak.easy.domain.PreferencesRepository
import org.speak.easy.domain.models.ThemeType

interface ThemeUseCase {
    suspend fun setTheme(theme: ThemeType)
    fun observeTheme(): Flow<ThemeType>
}

class DefaultThemeUseCase(private val repository: PreferencesRepository) : ThemeUseCase {
    override suspend fun setTheme(theme: ThemeType) {
        val currentPreferences = repository.getCurrentPreferences()
        repository.setPreferences(currentPreferences.copy(theme = theme))
    }

    override fun observeTheme(): Flow<ThemeType> {
        return repository.observePreferences().map { it.theme }
    }
}