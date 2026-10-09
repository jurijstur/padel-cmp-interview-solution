import io.bifri.interview.cmp.padel.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<KotlinMultiplatformExtension>(::configureKoin)
        }
    }
}

private fun Project.configureKoin(extension: KotlinMultiplatformExtension) {
    extension.apply {
        sourceSets {
            commonMain.dependencies {
                implementation(libs.findLibrary("koin.core").get())
            }
            androidMain.dependencies {
                implementation(libs.findLibrary("koin.android").get())
            }
        }
    }
}
