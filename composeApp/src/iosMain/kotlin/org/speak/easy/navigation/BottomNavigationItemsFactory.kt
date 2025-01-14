package org.speak.easy.navigation

import org.speak.easy.di.ScreenRoutesProvider
import speakeasy.ui_core.generated.resources.Res
import speakeasy.ui_core.generated.resources.history
import speakeasy.ui_core.generated.resources.main
import speakeasy.ui_core.generated.resources.mdi_translate

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