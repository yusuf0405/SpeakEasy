package org.speak.easy.core.designsystem.locale

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService

class AndroidAppLocaleManager(
    private val context: Context,
) : AppLocaleManager {

    private val localManager = context.getSystemService<LocaleManager>()

    override fun getLocale(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val locales = localManager?.applicationLocales ?: return "en"
            if (locales.isEmpty) "en" else
                locales[0]?.toLanguageTag()?.split("-")?.firstOrNull() ?: "en"
        } else {
            AppCompatDelegate.getApplicationLocales()
                .toLanguageTags().split("-")
                .firstOrNull() ?: "en"
        }
    }
}

@Composable
actual fun rememberAppLocale(): AppLang {
    val context = LocalContext.current
    val locale = AndroidAppLocaleManager(context).getLocale()
    return remember(locale) {
        locale.toApLang()
    }
}

private fun String.toApLang(): AppLang {
    return when (this) {
        "ru" -> AppLang.Russian
        else -> AppLang.English
    }
}