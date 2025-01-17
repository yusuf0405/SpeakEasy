package org.speak.easy.core.designsystem.locale

import androidx.compose.runtime.Composable

interface AppLocaleManager {
    fun getLocale(): String
}

@Composable
expect fun rememberAppLocale(): AppLang