package org.speak.easy.core

import android.content.Intent


private class AndroidTextSharingManager(
    private var platformConfiguration: PlatformConfiguration
) : TextSharingManager {

    override fun shareText(text: String) {
        val activityContext = platformConfiguration.activityContextFlow.value ?: return
        val intent = Intent().apply {
            setAction(Intent.ACTION_SEND)
            setType("text/plain")
            putExtra(Intent.EXTRA_TEXT, text)
        }
        activityContext.startActivity(Intent.createChooser(intent, "Share via"))
    }
}

actual fun provideTextShareManager(
    platformConfiguration: PlatformConfiguration
): TextSharingManager = AndroidTextSharingManager(platformConfiguration)