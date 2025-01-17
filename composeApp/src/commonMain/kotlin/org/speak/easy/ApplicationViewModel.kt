package org.speak.easy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.speak.easy.domain.models.ThemeType
import org.speak.easy.domain.usecases.ThemeUseCase

class ApplicationViewModel(
    private val themeUseCase: ThemeUseCase
) : ViewModel() {

    val themeState = themeUseCase
        .observeTheme()
        .stateIn(viewModelScope, SharingStarted.Lazily, (ThemeType.SYSTEM))
}