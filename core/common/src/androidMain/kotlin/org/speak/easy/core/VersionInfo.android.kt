package org.speak.easy.core

import android.content.pm.PackageManager

actual class VersionInfo actual constructor(private val platformConfiguration: PlatformConfiguration) {

    private val context by lazy { platformConfiguration.androidContext }

    actual val appVersion: String
        get() {
            return try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionName ?: "Unknown"
            } catch (e: PackageManager.NameNotFoundException) {
                "Unknown"
            }
        }
}
