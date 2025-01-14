package org.speak.easy.core

import platform.UIKit.UIActivityViewController

private class IosTextSharingManager(
    private val platformConfiguration: PlatformConfiguration,
) : TextSharingManager {

    override fun shareText(text: String) {
        val activityViewController = UIActivityViewController(
            activityItems = listOf(text),
            applicationActivities = null
        )
        platformConfiguration.viewController.presentViewController(
            activityViewController,
            animated = true,
            completion = null
        )
    }
}

actual fun provideTextShareManager(
    platformConfiguration: PlatformConfiguration
): TextSharingManager = IosTextSharingManager(platformConfiguration)