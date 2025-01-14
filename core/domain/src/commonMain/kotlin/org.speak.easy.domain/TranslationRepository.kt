package org.speak.easy.domain

import kotlinx.coroutines.flow.Flow
import org.speak.easy.domain.models.LanguageDomain
import org.speak.easy.domain.models.LanguageType
import org.speak.easy.domain.models.SelectedLanguageDomain
import org.speak.easy.domain.models.TranslateRequestBodyDomain
import org.speak.easy.domain.models.TranslationDomain
import org.speak.easy.domain.models.TranslationHistoryDomain

interface TranslationRepository {

    fun observeTranslationHistory(): Flow<List<TranslationHistoryDomain>>

    suspend fun translateText(body: TranslateRequestBodyDomain): List<TranslationDomain>

    suspend fun fetchLanguages(type: LanguageType): List<LanguageDomain>

    suspend fun updateSelectedLanguage(selectedLanguage: SelectedLanguageDomain)

    suspend fun fetchLatestSelectedLanguage(): SelectedLanguageDomain

    fun observeSelectedLanguageData(): Flow<SelectedLanguageDomain>

    suspend fun removeHistoryById(id: Long)

    suspend fun clearHistory()
}