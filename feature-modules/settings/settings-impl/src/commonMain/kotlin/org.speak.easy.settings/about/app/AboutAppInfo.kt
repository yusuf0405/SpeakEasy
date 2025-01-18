package org.speak.easy.settings.about.app

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.app_description
import speakeasy.core.ui.generated.resources.speak_easy

@Immutable
data class AboutAppInfo(
    val appName: StringResource,
    val description: StringResource,
    val version: String,
    val telegram: String?,
    val email: String?,
) {
    companion object {
        val unknown = AboutAppInfo(
            appName = Res.string.speak_easy,
            description = Res.string.app_description,
            version = "Unknown",
            telegram = "",
            email = ""
        )
    }
}