package org.speak.easy.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.speak.easy.core.exstensions.launchSafe
import org.speak.easy.core.exstensions.onError
import org.speak.easy.domain.TranslationRepository
import org.speak.easy.history.mappers.TranslationHistoryDomainToUiMapper
import org.speak.easy.history.models.HistoryModel
import org.speak.easy.speech.api.TextToSpeechManager

internal class HistoryViewModel(
    private val translationRepository: TranslationRepository,
    private val translationHistoryDomainToUiMapper: TranslationHistoryDomainToUiMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState.unknown)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private val searchQueryFlow = _uiState
        .map { it.searchQuery }
        .stateIn(viewModelScope, SharingStarted.Lazily, String())

    private var allLoadedHistories: List<HistoryModel> = emptyList()

    init {
        translationRepository
            .observeTranslationHistory()
            .map { histories -> histories.map(translationHistoryDomainToUiMapper::map) }
            .onEach { translationHistories ->
                allLoadedHistories = translationHistories
                _uiState.update { state -> state.copy(histories = translationHistories) }
            }
            .onError {

            }
            .launchIn(viewModelScope)

        searchQueryFlow.debounce(200).onEach { query ->
            _uiState.update { state ->
                state.copy(
                    histories = if (query.isEmpty()) {
                        allLoadedHistories
                    } else {
                        state.histories.search(query)
                    }
                )
            }
        }.onError {

        }.launchIn(viewModelScope)
    }

    fun onAction(action: HistoryScreenAction) {
        when (action) {
            is HistoryScreenAction.OnSearch -> {
                _uiState.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is HistoryScreenAction.OnMoreIconClick -> Unit
            is HistoryScreenAction.OnDeleteIcon -> {
                viewModelScope.launchSafe {
                    translationRepository.removeHistoryById(action.item.id)
                }
            }
        }
    }

    private fun List<HistoryModel>.search(query: String): List<HistoryModel> = filter {
        it.sourceText.contains(
            query,
            ignoreCase = true
        ) || it.targetText.contains(
            query,
            ignoreCase = true
        )
    }
}