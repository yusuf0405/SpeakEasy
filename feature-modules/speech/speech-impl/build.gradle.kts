plugins {
    alias(libs.plugins.speak.easy.library.impl)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.ui)
            implementation(projects.core.designsystem)
            implementation(projects.core.domain)
            implementation(libs.lifecycle.runtime.compose)
            implementation(projects.featureModules.speech.speechApi)
        }
    }
}

android {
    namespace = "org.speak.easy.speech"
}