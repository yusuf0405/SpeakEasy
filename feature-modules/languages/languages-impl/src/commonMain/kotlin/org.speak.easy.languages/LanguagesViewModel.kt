package org.speak.easy.languages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import org.speak.easy.core.exstensions.onError
import org.speak.easy.domain.LanguageHistoryRepository
import org.speak.easy.domain.holders.LanguagesHolder
import org.speak.easy.domain.holders.LanguagesLoadState
import org.speak.easy.ui.components.mappers.LanguageDomainToUiMapper
import org.speak.easy.ui.components.models.LanguageUi

private const val SEARCH_DEBOUNCE_MILLS = 200L

internal class LanguagesViewModel(
    private val languageHistoryRepository: LanguageHistoryRepository,
    private val languagesHolder: LanguagesHolder,
    private val languageDomainToUiMapper: LanguageDomainToUiMapper
) : ViewModel() {

    private var _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.unknown)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val searchQueryFlow = _uiState
        .map { it.searchQuery }
        .stateIn(viewModelScope, SharingStarted.Lazily, String())

    private var sourceLanguages: List<LanguageUi> = emptyList()
    private var targetLanguages: List<LanguageUi> = emptyList()

    init {
        languageHistoryRepository.observeLanguageHistory()
            .map { it.map(languageDomainToUiMapper::map) }
            .onEach { languages ->
                _uiState.update { state ->
                    state.copy(historyLanguages = languages.toSet().toList())
                }
            }.onError {
                // TODO: Handle error
            }
            .launchIn(viewModelScope)

        languagesHolder.languagesLoadStateFlow.onEach { state ->
            when (state) {
                is LanguagesLoadState.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is LanguagesLoadState.Error -> {

                }

                is LanguagesLoadState.FinishError -> {
                    _uiState.update { it.copy(isError = true) }
                }

                is LanguagesLoadState.Success -> {
                    sourceLanguages = state.languagesModel.targetLanguages
                        .map(languageDomainToUiMapper::map)
                    targetLanguages = state.languagesModel.sourceLanguages
                        .map(languageDomainToUiMapper::map)

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            targetLanguages = targetLanguages,
                            sourceLanguages = sourceLanguages
                        )
                    }
                }

                is LanguagesLoadState.Initial -> Unit
            }
        }.onError {
            // TODO: Handle error
        }.launchIn(viewModelScope)

        searchQueryFlow.debounce(SEARCH_DEBOUNCE_MILLS).onEach { query ->
            _uiState.update { state ->
                state.copy(
                    sourceLanguages = if (query.isEmpty()) {
                        sourceLanguages
                    } else {
                        sourceLanguages.search(query)
                    },
                    targetLanguages = if (query.isEmpty()) {
                        targetLanguages
                    } else {
                        targetLanguages.search(query)
                    }
                )
            }
        }.onError {
            // TODO: Handle error
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LanguageScreenAction) {
        when (action) {
            is LanguageScreenAction.OnClearSearchQuery -> {
                _uiState.update { it.copy(searchQuery = "") }
            }

            is LanguageScreenAction.OnSearch -> {
                _uiState.update { it.copy(searchQuery = action.query) }
            }
        }
    }


    private fun List<LanguageUi>.search(query: String): List<LanguageUi> = filter {
        it.name.contains(
            query,
            ignoreCase = true
        )
    }
}