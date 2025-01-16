import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.speak.easy.library.impl)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.kotlin.serialization)
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
            implementation(projects.core.common)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.core)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
android {
    namespace = "org.speak.easy.core.network"
}

buildkonfig {
    packageName = "org.speak.easy.core.network"

    defaultConfigs {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "API_KEY",
            value = properties["api-key"].toString()
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "BASE_URL",
            value = properties["deepl-cloud-url"].toString()
        )
    }
}