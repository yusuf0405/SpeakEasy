plugins {
    alias(libs.plugins.speak.easy.library.api)
    alias(libs.plugins.speak.easy.library.compose)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
        }
        commonMain.dependencies {
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.datetime)
            implementation(libs.coil.compose.core)
        }
    }
}

android {
    namespace = "org.speak.easy.core.designsystem"
}