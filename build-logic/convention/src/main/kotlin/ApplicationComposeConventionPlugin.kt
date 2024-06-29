import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.speak.easy.libs
import org.speak.easy.configureComposeMultiplatform
import org.speak.easy.configureAndroidApplication

class ApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("android-application").get().get().pluginId)
            apply(libs.findPlugin("compose-multiplatform").get().get().pluginId)
            apply(libs.findPlugin("compose-compiler").get().get().pluginId)
        }
        extensions.configure<KotlinMultiplatformExtension>(::configureComposeMultiplatform)
        extensions.configure<ApplicationExtension>(::configureAndroidApplication)
    }
}

