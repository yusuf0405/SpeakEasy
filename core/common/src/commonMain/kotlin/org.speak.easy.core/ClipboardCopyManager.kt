package org.speak.easy.core

interface ClipboardCopyManager {
    fun setClipboard(text: String)
}

expect fun provideClipboardUpdateManager(platformConfiguration: PlatformConfiguration): ClipboardCopyManager