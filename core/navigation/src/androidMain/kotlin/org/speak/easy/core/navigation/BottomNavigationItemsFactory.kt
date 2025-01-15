package org.speak.easy.core.navigation

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
            route = Destination.HistoryGraph.route
        ),
        BottomNavigationItem(
            icon = Res.drawable.mdi_translate,
            title = Res.string.main,
            route = Destination.TranslatorGraph.route
        ),
        BottomNavigationItem(
            icon = Res.drawable.photo_camera,
            title = Res.string.camera,
            route = Destination.CameraGraph.route
        ),
//        BottomNavigationItem(
//            icon = Res.drawable.settings,
//            title = Res.string.settings,
//            route = Destination.SettingsGraph.route
//        )
    )
}