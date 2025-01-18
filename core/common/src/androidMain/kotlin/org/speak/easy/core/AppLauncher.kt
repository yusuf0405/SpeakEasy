package org.speak.easy.core

import android.content.Intent
import android.net.Uri

actual class AppLauncher actual constructor(private val platformConfiguration: PlatformConfiguration) {

    private val context by lazy { platformConfiguration.androidContext }

    actual fun openEmail(email: String, subject: String?, body: String?) {
        val uri = Uri.parse("mailto:$email").buildUpon().apply {
            subject?.let { appendQueryParameter("subject", it) }
            body?.let { appendQueryParameter("body", it) }
        }.build()

        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    actual fun openTelegram(username: String) {
        val uri = Uri.parse("tg://resolve?domain=$username")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/$username"))
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(browserIntent)
        }
    }
}
