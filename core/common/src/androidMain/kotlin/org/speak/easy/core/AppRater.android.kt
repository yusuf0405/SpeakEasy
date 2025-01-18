package org.speak.easy.core

import android.content.Intent
import android.net.Uri

actual class AppRater actual constructor(private val platformConfiguration: PlatformConfiguration) {

    private val context by lazy { platformConfiguration.androidContext }
    actual fun openStorePage() {
        val packageName = context.packageName
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(browserIntent)
        }
    }
}
