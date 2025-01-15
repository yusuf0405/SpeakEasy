plugins {
    alias(libs.plugins.speak.easy.library.impl)
    alias(libs.plugins.speak.easy.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.ui)
            implementation(projects.core.navigation)
            implementation(projects.core.designsystem)
            implementation(projects.core.domain)
        }
    }
}

android {
    namespace = "org.speak.easy.ui.components"
}