plugins {
    alias(libs.plugins.speak.easy.library.api)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}

android {
    namespace = "org.speak.easy.translator.api"
}