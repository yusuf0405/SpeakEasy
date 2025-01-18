package org.speak.easy.core


import platform.Foundation.NSBundle
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class AppRater actual constructor(platformConfiguration: PlatformConfiguration) {

    actual fun openStorePage() {
        val appId = getAppStoreId() ?: return
        val appStoreUrl = "itms-apps://itunes.apple.com/app/id$appId?action=write-review"
        openUrl(appStoreUrl)
    }

    private fun getAppStoreId(): String? {
        return NSBundle.mainBundle.objectForInfoDictionaryKey("AppStoreID") as? String
    }

    private fun openUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url) ?: return
        UIApplication.sharedApplication.openURL(nsUrl, emptyMap<Any?, Any>()) { success ->
            println("Store URL Opened: $success")
        }
    }
}
