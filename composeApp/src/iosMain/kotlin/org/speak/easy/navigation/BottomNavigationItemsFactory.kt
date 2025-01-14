package org.speak.easy.navigation

import org.speak.easy.di.ScreenRoutesProvider
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.history
import speakeasy.core.ui.generated.resources.main
import speakeasy.core.ui.generated.resources.mdi_translate

actual class BottomNavigationItemsFactory {
    actual fun create(): List<BottomNavigationItem> = listOf(
        BottomNavigationItem(
            icon = Res.drawable.history,
            title = Res.string.history,
            route = ScreenRoutesProvider.getHistoryRoute()
        ),
        BottomNavigationItem(
            icon = Res.drawable.mdi_translate,
            title = Res.string.main,
            route = ScreenRoutesProvider.getTranslatorRoute()
        )
    )
}