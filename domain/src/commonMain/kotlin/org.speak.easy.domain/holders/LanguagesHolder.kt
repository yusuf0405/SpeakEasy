package org.speak.easy.domain.holders

import kotlinx.coroutines.flow.Flow

interface LanguagesHolder {

    val languagesLoadStateFlow: Flow<LanguagesLoadState>

    suspend fun fetchLanguages()
}