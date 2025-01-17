package org.speak.easy.domain

import kotlinx.coroutines.flow.Flow
import org.speak.easy.domain.models.LanguageDomain

interface LanguageHistoryRepository {
    fun observeLanguageHistory(): Flow<List<LanguageDomain>>
    suspend fun clearHistory()
    suspend fun addLanguage(language: LanguageDomain)
}