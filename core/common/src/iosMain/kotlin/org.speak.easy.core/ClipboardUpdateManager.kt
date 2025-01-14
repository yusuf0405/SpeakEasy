package org.speak.easy.core

import platform.UIKit.UIPasteboard

private class IosClipboardCopyManager : ClipboardCopyManager {
    override fun setClipboard(text: String) {
        val pasteBoard = UIPasteboard.generalPasteboard
        pasteBoard.string = text
    }
}

actual fun provideClipboardUpdateManager(
    platformConfiguration: PlatformConfiguration
): ClipboardCopyManager = IosClipboardCopyManager()