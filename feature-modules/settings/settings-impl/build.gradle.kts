import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.speak.easy.library.impl)
    alias(libs.plugins.speak.easy.library.compose)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.ui)
            implementation(projects.core.designsystem)
            implementation(projects.core.navigation)
            implementation(projects.core.domain)
            implementation(projects.core.analytics)
            implementation(projects.featureModules.settings.settingsApi)
            implementation(projects.featureModules.permission.permissionApi)
        }
    }
}

android {
    namespace = "org.speak.easy.settings"
}

buildkonfig {
    packageName = "org.speak.easy.settings"
    defaultConfigs {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "TELEGRAM_CONTACT",
            value = properties["telegram-contact"].toString()
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "EMAIL_CONTACT",
            value = properties["email-contact"].toString()
        )
    }
}