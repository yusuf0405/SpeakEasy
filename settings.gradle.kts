rootProject.name = "SpeakEasy"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
}

include(
    ":composeApp",
    ":core",
    ":ui-core",
    ":data",
    ":domain",
    ":feature-modules:translator:translator-api",
    ":feature-modules:translator:translator-impl",
    ":feature-modules:history:history-api",
    ":feature-modules:history:history-impl",
    ":feature-modules:speech:speech-api",
    ":feature-modules:speech:speech-impl",
)