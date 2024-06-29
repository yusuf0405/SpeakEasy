import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.speak.easy.configureAndroidLibrary
import org.speak.easy.configureLibraryImplKotlinMultiplatform
import org.speak.easy.configureLibraryKotlinMultiplatform
import org.speak.easy.libs

class LibraryImplConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("android-library").get().get().pluginId)
            apply(libs.findPlugin("kotlin-multiplatform").get().get().pluginId)
            apply(libs.findPlugin("kotlin-serialization").get().get().pluginId)
        }
        extensions.configure<KotlinMultiplatformExtension>(::configureLibraryKotlinMultiplatform)
        extensions.configure<KotlinMultiplatformExtension>(::configureLibraryImplKotlinMultiplatform)
        extensions.configure<LibraryExtension>(::configureAndroidLibrary)
    }
}
