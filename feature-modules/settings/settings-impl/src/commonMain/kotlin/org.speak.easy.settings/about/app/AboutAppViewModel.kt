package org.speak.easy.settings.about.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import org.speak.easy.core.AppLauncher
import org.speak.easy.core.DispatcherProvider
import org.speak.easy.core.exstensions.launchSafe
import org.speak.easy.core.exstensions.stateInWhileSubscribed

class AboutAppViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val aboutAppInfoFactory: AboutAppInfoFactory,
    private val appLauncher: AppLauncher
) : ViewModel() {

    private val _uiState: MutableStateFlow<AboutAppInfo> = MutableStateFlow(AboutAppInfo.unknown)
    val uiState = _uiState
        .onStart { getAppInfo() }
        .flowOn(dispatcherProvider.io)
        .stateInWhileSubscribed(viewModelScope, AboutAppInfo.unknown)

    private fun getAppInfo() {
        viewModelScope.launchSafe {
            _uiState.tryEmit(aboutAppInfoFactory.create())
        }
    }

    fun openTelegram(userName: String) {
        appLauncher.openTelegram(userName)
    }

    fun openEmail(email: String) {
        appLauncher.openEmail(email)
    }
}