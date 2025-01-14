import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.speak.easy.library.api)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
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

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.core)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.napier)
            implementation(libs.okio)
            implementation(libs.kotlinx.datetime)
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.sqlite)
            implementation(libs.paging.compose.common)
            implementation(libs.paging.common)

            api(libs.datastore.preferences.core)
            api(libs.koin.core)
            api(libs.datastore.core.okio)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            api(libs.koin.android)
            implementation(libs.ktor.client.android)
            implementation(libs.room.paging)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

val kspConfigurations = listOf(
    "kspAndroid",
    "kspIosSimulatorArm64",
    "kspIosX64",
    "kspIosArm64"
)

dependencies {
    kspConfigurations.forEach { configuration ->
        add(configuration, libs.room.compiler)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "org.speak.easy.data"
}

buildkonfig {
    packageName = "org.speak.easy.data"

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