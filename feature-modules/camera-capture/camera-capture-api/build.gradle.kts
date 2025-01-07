plugins {
    alias(libs.plugins.speak.easy.library.api)
    alias(libs.plugins.speak.easy.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
            implementation(projects.uiComponents)
        }
    }
}

android {
    namespace = "org.speak.easy.camera.capture.api"
}