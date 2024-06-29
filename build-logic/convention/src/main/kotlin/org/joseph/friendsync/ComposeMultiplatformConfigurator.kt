package org.speak.easy

import org.gradle.api.Project
import org.gradle.kotlin.dsl.all
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureComposeMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    val composeDeps = extensions.getByType<ComposeExtension>().dependencies
    sourceSets.apply {
        all {
            languageSettings {
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain {
            dependencies {
                implementation(libs.findLibrary("composeIcons-featherIcons").get())
                api(composeDeps.components.resources)
                implementation(composeDeps.ui)
                implementation(composeDeps.foundation)
                implementation(composeDeps.runtime)
                implementation(composeDeps.animation)
                implementation(composeDeps.animationGraphics)
                implementation(composeDeps.material3)
                implementation(composeDeps.materialIconsExtended)
                implementation(composeDeps.components.uiToolingPreview)
            }
        }
    }
}