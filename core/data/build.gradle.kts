import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.speak.easy.library.impl)
}

kotlin {
    jvmToolchain(17)
}

kotlin {
    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_1_9)
    }
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.common)
            implementation(projects.core.datastore)
            implementation(projects.core.database)
            implementation(projects.core.network)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.core)
            implementation(libs.room.runtime)
        }
    }
}

android {
    namespace = "org.speak.easy.data"
}