package org.speak.easy.core

interface TextSharingManager {
    fun shareText(text: String)
}

expect fun provideTextShareManager(platformConfiguration: PlatformConfiguration): TextSharingManager