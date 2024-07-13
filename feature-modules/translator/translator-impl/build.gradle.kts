
plugins {
    alias(libs.plugins.speak.easy.library.impl)
    alias(libs.plugins.speak.easy.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
            implementation(projects.uiCore)
            implementation(projects.domain)
            implementation(projects.uiComponents)
            implementation(projects.featureModules.translator.translatorApi)
            implementation(projects.featureModules.speech.speechApi)
            implementation(projects.featureModules.languages.languagesApi)
        }
    }
}

android {
    namespace = "org.speak.easy.translator"
}