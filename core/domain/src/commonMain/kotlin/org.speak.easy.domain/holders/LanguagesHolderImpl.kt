package org.speak.easy.domain.holders

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.speak.easy.domain.TranslationRepository
import org.speak.easy.domain.models.LanguageType
import org.speak.easy.domain.models.LanguagesModel

internal const val MAX_ITERATIONS = 5

sealed interface LanguagesLoadState {
    data object Initial : LanguagesLoadState
    data object Loading : LanguagesLoadState
    data object Error : LanguagesLoadState
    data object FinishError : LanguagesLoadState
    data class Success(val languagesModel: LanguagesModel) : LanguagesLoadState
}

class LanguagesHolderImpl(
    private val translationRepository: TranslationRepository
) : LanguagesHolder {

    private val _languagesLoadStateFlow =
        MutableStateFlow<LanguagesLoadState>(LanguagesLoadState.Initial)
    override val languagesLoadStateFlow: Flow<LanguagesLoadState> =
        _languagesLoadStateFlow.asStateFlow()

    override suspend fun fetchLanguages() {
        val state = _languagesLoadStateFlow.value
        if (state is LanguagesLoadState.Success || state is LanguagesLoadState.Loading) return
        var iterationCount = 0
        while (iterationCount < MAX_ITERATIONS) {
            try {
                _languagesLoadStateFlow.emit(LanguagesLoadState.Loading)
                val languages = loadLanguages()
                _languagesLoadStateFlow.emit(LanguagesLoadState.Success(languages))
                break
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _languagesLoadStateFlow.emit(LanguagesLoadState.Error)
                iterationCount++
            }
        }
        if (iterationCount == MAX_ITERATIONS) {
            _languagesLoadStateFlow.emit(LanguagesLoadState.FinishError)
        }
    }

    private suspend fun loadLanguages(): LanguagesModel {
        val state = _languagesLoadStateFlow.value
        if (state is LanguagesLoadState.Success) return state.languagesModel
        val (sourceLanguages, targetLanguages) = coroutineScope {
            val sourceDeferred = async {
                translationRepository.fetchLanguages(LanguageType.source)
            }
            val targetDeferred = async {
                translationRepository.fetchLanguages(LanguageType.target)
            }

            Pair(sourceDeferred.await(), targetDeferred.await())
        }

        return LanguagesModel(
            sourceLanguages = sourceLanguages.sortedBy { it.name },
            targetLanguages = targetLanguages.sortedBy { it.name })
    }
}