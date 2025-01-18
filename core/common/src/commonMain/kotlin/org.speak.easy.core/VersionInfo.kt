package org.speak.easy.core

expect class VersionInfo constructor(platformConfiguration: PlatformConfiguration) {
    val appVersion: String
}