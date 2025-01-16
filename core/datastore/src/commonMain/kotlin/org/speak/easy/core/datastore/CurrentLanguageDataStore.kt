package org.speak.easy.core.datastore

import kotlinx.coroutines.flow.Flow
import org.speak.easy.core.datastore.models.SelectedLanguageDto

interface CurrentLanguageDataStore {

    suspend fun updateSelectedLanguage(selectedLanguage: SelectedLanguageDto)

    suspend fun getSelectedLanguageData(): SelectedLanguageDto

    fun observeSelectedLanguageData(): Flow<SelectedLanguageDto>
}