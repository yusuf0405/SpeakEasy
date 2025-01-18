package org.speak.easy.settings.about.app

import org.speak.easy.core.VersionInfo
import org.speak.easy.settings.BuildKonfig
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.app_description
import speakeasy.core.ui.generated.resources.speak_easy

interface AboutAppInfoFactory {
    suspend fun create(): AboutAppInfo
}

class DefaultAboutAppInfoFactory(
    private val versionInfo: VersionInfo
) : AboutAppInfoFactory {

    override suspend fun create(): AboutAppInfo = AboutAppInfo(
        version = versionInfo.appVersion,
        appName = Res.string.speak_easy,
        description = Res.string.app_description,
        telegram = BuildKonfig.TELEGRAM_CONTACT,
        email = BuildKonfig.EMAIL_CONTACT
    )
}