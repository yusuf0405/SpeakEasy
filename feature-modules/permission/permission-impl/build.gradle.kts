plugins {
    alias(libs.plugins.speak.easy.library.impl)
    alias(libs.plugins.speak.easy.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.ui)
            implementation(projects.core.designsystem)
            implementation(projects.featureModules.permission.permissionApi)
        }
        androidMain.dependencies {
            implementation(libs.accompanist.permissions)
        }
    }
}

android {
    namespace = "org.speak.easy.permission"
}