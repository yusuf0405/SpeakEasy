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

includeSubprojects()

fun includeSubprojects() {
    val moduleBlackList = listOf("infrastructure")
    initModules(rootDir, depth = 1, moduleBlackList)
    includeModulesIn("apps", depth = 1)
    includeModulesIn("feature-modules", depth = 2)
    includeModulesIn("core", depth = 2)
}

fun includeModulesIn(directoryName: String, depth: Int) {
    initModules(File(rootDir, directoryName), depth)
}

/**
 * Automatically registers project modules
 *
 * @param sourceDir root directory for module search
 * @param depth maximum recursion depth
 * @* @param module BlackList list of excluded modules
 */
fun initModules(sourceDir: File, depth: Int, moduleBlackList: List<String> = emptyList()) {
    sourceDir.walk()
        .maxDepth(depth)
        .filter { it.shouldIncludeModule(sourceDir, moduleBlackList) }
        .forEach { moduleDir ->
            val moduleName = moduleDir.toModuleName()
            include(moduleName)
            project(moduleName).projectDir = moduleDir
        }
}

fun File.shouldIncludeModule(sourceDir: File, moduleBlackList: List<String>): Boolean {
    return name !in moduleBlackList &&
            isDirectory &&
            this != sourceDir &&
            isGradleProject()
}

fun File.toModuleName(): String {
    return path.substringAfter(rootDir.name).replace(File.separatorChar, ':')
}

fun File.isGradleProject(): Boolean {
    return isDirectory &&
            listOf("build.gradle.kts", "build.gradle").any { File(this, it).exists() }
}