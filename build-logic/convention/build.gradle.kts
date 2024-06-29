import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "org.speak.easy.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("libraryApi") {
            id = "org.speak.easy.library.api"
            implementationClass = "LibraryApiConventionPlugin"
        }
        register("libraryImpl") {
            id = "org.speak.easy.library.impl"
            implementationClass = "LibraryImplConventionPlugin"
        }
        register("libraryCompose") {
            id = "org.speak.easy.library.compose"
            implementationClass = "LibraryComposeConventionPlugin"
        }
        register("applicationCompose") {
            id = "org.speak.easy.application.compose"
            implementationClass = "ApplicationComposeConventionPlugin"
        }
    }
}