package org.speak.easy.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import platform.Foundation.setValue
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarStyle

@Composable
internal actual fun SystemAppearance(isDarkTheme: Boolean) {
    val window = UIApplication.sharedApplication.keyWindow
    val rootViewController = window?.rootViewController

    rootViewController?.setNeedsStatusBarAppearanceUpdate()
    setStatusBarStyle(isDarkTheme)
}

private fun setStatusBarStyle(isDarkTheme: Boolean) {
    UIApplication.sharedApplication.setStatusBarStyle(
        if (isDarkTheme) {
            UIStatusBarStyleDarkContent
        } else {
            UIStatusBarStyleLightContent
        },
        true
    )

    UIApplication.sharedApplication.keyWindow?.rootViewController?.setNeedsStatusBarAppearanceUpdate()
}