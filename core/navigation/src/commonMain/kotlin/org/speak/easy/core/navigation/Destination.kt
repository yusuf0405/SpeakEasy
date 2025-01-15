package org.speak.easy.core.navigation

sealed class Destination(val route: String) {

    data object TranslatorGraph : Destination("translator_route")

    data object CameraGraph : Destination("camera_route")

    data object SettingsGraph : Destination("settings_route")

    data object HistoryGraph : Destination("history_route")
}