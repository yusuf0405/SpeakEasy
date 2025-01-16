plugins {
    alias(libs.plugins.speak.easy.library.impl)
    alias(libs.plugins.speak.easy.library.compose)
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
        }
    }
}

android {
    namespace = "org.speak.easy.settings"
}