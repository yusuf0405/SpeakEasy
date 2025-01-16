plugins {
    alias(libs.plugins.speak.easy.library.api)
    alias(libs.plugins.speak.easy.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
        }
    }
}

android {
    namespace = "org.speak.easy.core.analytics"
}