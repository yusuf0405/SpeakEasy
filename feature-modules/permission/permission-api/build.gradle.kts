plugins {
    alias(libs.plugins.speak.easy.library.api)
    alias(libs.plugins.speak.easy.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
        }
    }
}

android {
    namespace = "org.speak.easy.permission.api"
}