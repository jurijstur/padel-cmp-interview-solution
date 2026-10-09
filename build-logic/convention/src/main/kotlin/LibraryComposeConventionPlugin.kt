import io.bifri.interview.cmp.padel.configureCompose
import io.bifri.interview.cmp.padel.findPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class LibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(findPluginId("compose.compiler"))
                apply(findPluginId("compose.multiplatform"))
            }

            extensions.configure<KotlinMultiplatformExtension>(::configureCompose)
        }
    }
}
