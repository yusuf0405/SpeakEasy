package org.speak.easy.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import org.speak.easy.core.DispatcherProvider
import org.speak.easy.core.exstensions.launchSafe
import org.speak.easy.core.exstensions.stateInWhileSubscribed
import org.speak.easy.core.navigation.Destination
import org.speak.easy.core.navigation.Navigator
import org.speak.easy.settings.category.CategoryFactory
import org.speak.easy.settings.category.CategoryType

class SettingsViewModel(
    private val categoryFactory: CategoryFactory,
    private val dispatcherProvider: DispatcherProvider,
    private val navigator: Navigator
) : ViewModel() {

    private val _launchSetting = MutableSharedFlow<Boolean>()
    val launchSetting: SharedFlow<Boolean> = _launchSetting.asSharedFlow()

    private val _uiState: MutableStateFlow<SettingsUiState> =
        MutableStateFlow(SettingsUiState.default)
    val uiState = _uiState
        .onStart { loadCategories() }
        .flowOn(dispatcherProvider.io)
        .stateInWhileSubscribed(viewModelScope, SettingsUiState.default)

    private fun loadCategories() {
        _uiState.update { state ->
            state.copy(
                categories = categoryFactory.categories(),
                moreCategories = categoryFactory.moreCategories()
            )
        }
    }

    fun onAction(type: CategoryType) {
        when (type) {
            CategoryType.LANGUAGE -> showSettings()
            CategoryType.THEME -> navigateToChangeThemeScreen()
            CategoryType.CLEAN_HISTORY -> {}
            CategoryType.HELP_SUPPORT -> {}
            CategoryType.ABOUT_APP -> {}
        }
    }

    private fun navigateToChangeThemeScreen() {
        viewModelScope.launchSafe {
            navigator.navigate(Destination.ChangeThemeScreen)
        }
    }

    private fun showSettings() {
        viewModelScope.launchSafe {
            _launchSetting.emit(true)
        }
    }

    fun settingsShowed() {
        viewModelScope.launchSafe {
            _launchSetting.emit(false)
        }
    }
}