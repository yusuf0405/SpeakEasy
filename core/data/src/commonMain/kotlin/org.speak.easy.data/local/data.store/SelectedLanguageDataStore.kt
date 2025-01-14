package org.speak.easy.data.local.data.store

import kotlinx.coroutines.flow.Flow
import org.speak.easy.data.models.SelectedLanguageData

internal interface SelectedLanguageDataStore {

    suspend fun updateSelectedLanguage(selectedLanguage: SelectedLanguageData)

    suspend fun getSelectedLanguageData(): SelectedLanguageData

    fun observeSelectedLanguageData(): Flow<SelectedLanguageData>
}