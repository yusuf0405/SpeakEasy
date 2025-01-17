package org.speak.easy.core.designsystem.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

class IosAppLocaleManager : AppLocaleManager {
    override fun getLocale(): String {
        val nsLocale = NSLocale.currentLocale.languageCode
        return nsLocale
    }
}

@Composable
actual fun rememberAppLocale(): AppLang {
    val nsLocale = IosAppLocaleManager().getLocale()
    return remember(nsLocale) {
        when (nsLocale) {
            "ru" -> AppLang.Russian
            else -> AppLang.English
        }
    }
}