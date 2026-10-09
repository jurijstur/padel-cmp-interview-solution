import io.bifri.interview.cmp.padel.configureCompose
import io.bifri.interview.cmp.padel.findPluginId
import io.bifri.interview.cmp.padel.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class ApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(findPluginId("compose.compiler"))
                apply(findPluginId("compose.multiplatform"))
            }

            extensions.configure<KotlinMultiplatformExtension>(::configureCompose)

            dependencies {
                "debugImplementation"(libs.findLibrary("compose.ui.tooling").get())
            }
        }
    }
}
