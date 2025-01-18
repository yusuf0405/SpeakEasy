package org.speak.easy.core

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class AppLauncher actual constructor(platformConfiguration: PlatformConfiguration) {

    actual fun openEmail(email: String, subject: String?, body: String?) {
        val subjectEncoded = subject?.encodeURL() ?: ""
        val bodyEncoded = body?.encodeURL() ?: ""
        val mailtoURL = "mailto:$email?subject=$subjectEncoded&body=$bodyEncoded"
        openUrl(mailtoURL)
    }

    actual fun openTelegram(username: String) {
        val telegramURL = "tg://resolve?domain=$username"
        if (!openUrl(telegramURL)) {
            openUrl("https://t.me/$username")
        }
    }

    private fun openUrl(url: String): Boolean {
        val nsUrl = NSURL.URLWithString(url) ?: return false

        return if (UIApplication.sharedApplication.canOpenURL(nsUrl)) {
            UIApplication.sharedApplication.openURL(nsUrl, emptyMap<Any?, Any>()) { success ->
                println("URL Opened: $success")
            }
            true
        } else {
            false
        }
    }

    private fun String.encodeURL(): String {
        return this.replace(" ", "%20")
    }
}