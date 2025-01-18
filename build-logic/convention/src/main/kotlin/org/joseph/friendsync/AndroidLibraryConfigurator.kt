package org.speak.easy

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureAndroidLibraryCompose(
    extension: LibraryExtension
): LibraryExtension = extension.apply {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =
            libs.findVersion("kotlinCompilerExtensionVersion").get().toString()
    }
}

internal fun Project.configureAndroidLibrary(
    extension: LibraryExtension
): LibraryExtension = extension.apply {
    compileSdk = libs.findVersion("android.compileSdk").get().toString().toInt()

    defaultConfig {
        minSdk = libs.findVersion("android.minSdk").get().toString().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

internal fun configureLibraryKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
): KotlinMultiplatformExtension = extension.apply {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
}

internal fun Project.configureLibraryImplKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
): KotlinMultiplatformExtension = extension.apply {
    sourceSets.apply {
        commonMain {
            dependencies {
                implementation(libs.findLibrary("lifecycle-viewmodel-compose").get())
                implementation(libs.findLibrary("lifecycle-runtime-compose").get())
                implementation(libs.findLibrary("navigation-compose").get())
                implementation(libs.findLibrary("koin-core").get())
                implementation(libs.findLibrary("koin-compose").get())
                implementation(libs.findLibrary("coil-compose").get())
                implementation(libs.findLibrary("kotlinx-datetime").get())
            }
        }
        androidMain.dependencies {
            implementation(libs.findLibrary("koin-android").get())
        }
    }
}