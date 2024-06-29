package org.speak.easy.data.local.data.store

import org.speak.easy.data.models.SelectedLanguageData

internal interface SelectedLanguageDataStore {

    suspend fun updateSelectedLanguage(selectedLanguage: SelectedLanguageData)

    suspend fun getSelectedLanguageData(): SelectedLanguageData
}