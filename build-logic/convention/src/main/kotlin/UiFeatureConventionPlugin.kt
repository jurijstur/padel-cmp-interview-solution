import io.bifri.interview.cmp.padel.findPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class UiFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(findPluginId("convention.library"))
                apply(findPluginId("convention.library.compose"))
            }
            extensions.configure<KotlinMultiplatformExtension>(::configureUiFeature)
        }
    }
}

private fun Project.configureUiFeature(extension: KotlinMultiplatformExtension) {
    extension.apply {
        sourceSets {
            commonMain.dependencies {
                implementation(project(":core:ui"))
            }
        }
    }
}
