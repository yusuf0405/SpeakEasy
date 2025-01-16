plugins {
    alias(libs.plugins.speak.easy.library.impl)
    alias(libs.plugins.speak.easy.library.compose)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.camera.camera2)
            implementation(libs.androidx.camera.lifecycle)
            implementation(libs.androidx.camera.view)
            implementation(libs.androidx.camera.mlkit.vision)
            implementation(libs.play.services.mlkit.text.recognition)
            implementation(libs.iconsax.android)
        }
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.ui)
            implementation(projects.core.designsystem)
            implementation(projects.core.navigation)
            implementation(projects.core.domain)
            implementation(projects.core.analytics)
            implementation(projects.core.uiComponents)
            implementation(projects.featureModules.cameraCapture.cameraCaptureApi)
            implementation(projects.featureModules.translator.translatorApi)
            implementation(projects.featureModules.permission.permissionApi)
        }
    }
}

android {
    namespace = "org.speak.easy.camera.capture"
}
