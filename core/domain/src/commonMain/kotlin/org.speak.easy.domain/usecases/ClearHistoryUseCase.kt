package org.speak.easy.domain.usecases

import org.speak.easy.domain.TranslationRepository

interface ClearHistoryUseCase {
    suspend fun clear()
}

class DefaultClearHistoryUseCase(
    private val repository: TranslationRepository
) : ClearHistoryUseCase {
    override suspend fun clear() {
        return repository.clearHistory()
    }
}