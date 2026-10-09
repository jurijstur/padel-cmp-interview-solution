import io.bifri.interview.cmp.padel.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class FileKitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<KotlinMultiplatformExtension>(::configureFileKit)
        }
    }
}

private fun Project.configureFileKit(extension: KotlinMultiplatformExtension) {
    extension.apply {
        sourceSets {
            commonMain.dependencies {
                implementation(libs.findLibrary("filekit.core").get())
            }
        }
    }
}
