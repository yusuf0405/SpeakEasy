plugins {
    alias(libs.plugins.speak.easy.library.impl)
    alias(libs.plugins.speak.easy.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
            implementation(projects.uiCore)
            implementation(projects.domain)
            implementation(projects.featureModules.history.historyApi)
            implementation(projects.featureModules.speech.speechApi)
        }
    }
}

android {
    namespace = "org.speak.easy.history"
}