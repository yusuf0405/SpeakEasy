package org.speak.easy.core

import android.content.ClipData
import android.content.ClipboardManager
import androidx.core.content.ContextCompat

private class AndroidClipboardCopyManager(
    private val platformConfiguration: PlatformConfiguration
) : ClipboardCopyManager {

    override fun setClipboard(text: String) {
        val clipboard = ContextCompat.getSystemService(
            platformConfiguration.androidContext,
            ClipboardManager::class.java
        )
        val clip = ClipData.newPlainText("label", text)
        clipboard?.setPrimaryClip(clip)
    }
}

actual fun provideClipboardUpdateManager(
    platformConfiguration: PlatformConfiguration
): ClipboardCopyManager = AndroidClipboardCopyManager(platformConfiguration)