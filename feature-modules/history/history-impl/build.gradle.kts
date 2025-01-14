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
            implementation(projects.core.domain)
            implementation(projects.featureModules.history.historyApi)
            implementation(projects.featureModules.speech.speechApi)
        }
    }
}

android {
    namespace = "org.speak.easy.history"
}