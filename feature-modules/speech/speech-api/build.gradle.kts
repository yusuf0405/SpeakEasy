plugins {
    alias(libs.plugins.speak.easy.library.api)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}

android {
    namespace = "org.speak.easy.speech.api"
}