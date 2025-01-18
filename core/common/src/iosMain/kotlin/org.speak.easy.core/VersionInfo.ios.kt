package org.speak.easy.core

import platform.Foundation.NSBundle

actual class VersionInfo actual constructor(platformConfiguration: PlatformConfiguration) {
    actual val appVersion: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String ?: "Unknown"
}