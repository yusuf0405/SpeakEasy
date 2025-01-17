package org.speak.easy.core.navigation

sealed class Destination(val route: String) {

    data object TranslatorGraph : Destination("translator_route")

    data object CameraGraph : Destination("camera_route")

    data object SettingsGraph : Destination("settings_graph_route")

    data object HistoryGraph : Destination("history_route")

    data object ChangeThemeScreen : Destination("change_theme_route")

    data object SettingsScreen : Destination("settings_screen_route")
}