import io.bifri.interview.cmp.padel.configureCoreDeps
import io.bifri.interview.cmp.padel.findPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class LibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(findPluginId("convention.library.core"))
            }
            extensions.configure<KotlinMultiplatformExtension>(::configureCoreDeps)
        }
    }
}
