package org.speak.easy.navigation

import org.speak.easy.di.ScreenRoutesProvider
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.camera
import speakeasy.core.ui.generated.resources.history
import speakeasy.core.ui.generated.resources.main
import speakeasy.core.ui.generated.resources.mdi_translate
import speakeasy.core.ui.generated.resources.photo_camera

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
        ),
        BottomNavigationItem(
            icon = Res.drawable.photo_camera,
            title = Res.string.camera,
            route = ScreenRoutesProvider.getCameraCaptureRoute()
        ),
//        BottomNavigationItem(
//            icon = Res.drawable.settings,
//            title = Res.string.settings,
//            route = ScreenRoutesProvider.getSettingsRoute()
//        )
    )
}