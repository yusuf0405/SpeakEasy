package org.speak.easy.permission.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

actual class UrlLauncher {
    actual fun openAppSettings() {
        val settingsUrl = NSURL.URLWithString(UIApplicationOpenSettingsURLString)
        if (settingsUrl != null) {
            UIApplication.sharedApplication.openURL(
                settingsUrl,
                mapOf<Any?, Any?>(),
                completionHandler = { success ->
                    println("Settings opened: $success")
                }
            )
        } else {
            println("Invalid settings URL")
        }
    }
}


@Composable
actual fun rememberUrlLauncher(): UrlLauncher {
    return remember {
        UrlLauncher()
    }
}