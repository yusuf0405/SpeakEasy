package org.speak.easy.core

expect class AppLauncher constructor(platformConfiguration: PlatformConfiguration) {
    fun openEmail(email: String, subject: String? = null, body: String? = null)
    fun openTelegram(username: String)
}
