plugins {
    alias(libs.plugins.speak.easy.library.impl)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
            implementation(projects.uiCore)
            implementation(projects.domain)
            implementation(libs.lifecycle.runtime.compose)
            implementation(projects.featureModules.speech.speechApi)
        }
    }
}

android {
    namespace = "org.speak.easy.speech"
}