package org.speak.easy.core.ui.extensions

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry.getString(key: String, default: String): String {
    return arguments?.getString(key) ?: default
}

fun NavBackStackEntry.getBoolean(key: String, default: Boolean): Boolean {
    return arguments?.getBoolean(key) ?: default
}