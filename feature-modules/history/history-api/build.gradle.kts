plugins {
    alias(libs.plugins.speak.easy.library.api)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
        }
    }
}

android {
    namespace = "org.speak.easy.history.api"
}