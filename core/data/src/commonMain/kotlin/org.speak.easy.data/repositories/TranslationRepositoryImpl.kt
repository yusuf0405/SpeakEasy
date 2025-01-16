package org.speak.easy.data.repositories

import kotlinx.coroutines.flow.Flow
import org.speak.easy.domain.TranslationRepository
import org.speak.easy.domain.models.LanguageDomain
import org.speak.easy.domain.models.LanguageType
import org.speak.easy.domain.models.SelectedLanguageDomain
import org.speak.easy.domain.models.TranslateRequestBodyDomain
import org.speak.easy.domain.models.TranslationDomain
import org.speak.easy.domain.models.TranslationHistoryDomain

internal class TranslationRepositoryImpl(
    private val translationHistoryManager: TranslationHistoryManager,
    private val translationService: TranslationService,
    private val languageService: LanguageService,
    private val currentLanguageManager: CurrentLanguageManager
) : TranslationRepository {

    override fun observeTranslationHistory(): Flow<List<TranslationHistoryDomain>> {
        return translationHistoryManager.observeTranslationHistory()
    }

    override suspend fun translateText(body: TranslateRequestBodyDomain): List<TranslationDomain> {
        return translationService.translateText(body)
    }

    override suspend fun fetchLanguages(type: LanguageType): List<LanguageDomain> {
        return languageService.fetchLanguages(type)
    }

    override suspend fun updateCurrentLanguage(selectedLanguage: SelectedLanguageDomain) {
        currentLanguageManager.updateSelectedLanguage(selectedLanguage)
    }

    override suspend fun fetchCurrentSelectedLanguage(): SelectedLanguageDomain {
        return currentLanguageManager.fetchLatestSelectedLanguage()
    }

    override fun observeCurrentLanguageData(): Flow<SelectedLanguageDomain> {
        return currentLanguageManager.observeSelectedLanguageData()
    }

    override suspend fun removeHistoryById(id: Long) {
        translationHistoryManager.removeHistoryById(id)
    }

    override suspend fun clearHistory() {
        translationHistoryManager.clearHistory()
    }
}