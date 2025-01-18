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
import org.speak.easy.core.AppLauncher
import org.speak.easy.core.AppRater
import org.speak.easy.core.DispatcherProvider
import org.speak.easy.core.exstensions.launchSafe
import org.speak.easy.core.exstensions.stateInWhileSubscribed
import org.speak.easy.core.navigation.Destination
import org.speak.easy.core.navigation.Navigator
import org.speak.easy.domain.usecases.ClearHistoryUseCase
import org.speak.easy.settings.category.CategoryFactory
import org.speak.easy.settings.category.CategoryType

class SettingsViewModel(
    private val categoryFactory: CategoryFactory,
    private val dispatcherProvider: DispatcherProvider,
    private val clearHistoryUseCase: ClearHistoryUseCase,
    private val appRater: AppRater,
    private val appLauncher: AppLauncher,
    private val navigator: Navigator
) : ViewModel() {

    private val _launchSetting = MutableSharedFlow<Boolean>()
    val launchSetting: SharedFlow<Boolean> = _launchSetting.asSharedFlow()

    private val _launchClearHistory = MutableSharedFlow<Boolean>()
    val launchClearHistory: SharedFlow<Boolean> = _launchClearHistory.asSharedFlow()

    private val _launchRatingApp = MutableSharedFlow<Boolean>()
    val launchRatingApp: SharedFlow<Boolean> = _launchRatingApp.asSharedFlow()

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
            CategoryType.CLEAN_HISTORY -> showClearHistoryPopup()
            CategoryType.RATE_APP -> showRatingApp()
            CategoryType.HELP_SUPPORT -> openEmail()
            CategoryType.ABOUT_APP -> navigateToAboutAppScreen()
        }
    }

    fun openRateStorePage() {
        appRater.openStorePage()
    }

    fun clearHistory() {
        viewModelScope.launchSafe {
            clearHistoryUseCase.clear()
        }
    }

    private fun navigateToChangeThemeScreen() {
        viewModelScope.launchSafe {
            navigator.navigate(Destination.ChangeThemeScreen)
        }
    }

    private fun navigateToAboutAppScreen() {
        viewModelScope.launchSafe {
            navigator.navigate(Destination.AboutAppScreen)
        }
    }

    private fun openEmail() {
        appLauncher.openEmail(BuildKonfig.EMAIL_CONTACT)
    }

    private fun showRatingApp() {
        viewModelScope.launchSafe {
            _launchRatingApp.emit(true)
        }
    }

    fun dismissRatingApp() {
        viewModelScope.launchSafe {
            _launchRatingApp.emit(false)
        }
    }

    private fun showClearHistoryPopup() {
        viewModelScope.launchSafe {
            _launchClearHistory.emit(true)
        }
    }

    fun dismissClearHistoryPopup() {
        viewModelScope.launchSafe {
            _launchClearHistory.emit(false)
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