import io.bifri.interview.cmp.padel.libs
import io.bifri.interview.cmp.padel.runBeforePreBuild
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
internal class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<DetektPlugin>()

            extensions.configure<DetektExtension> {
                config.from("$rootDir/config/detekt.yml")
                buildUponDefaultConfig = true
                parallel = true
                source.from(
                    objects.fileCollection().from(
                        "src/commonMain/kotlin",
                        "src/commonTest/kotlin",
                        "src/androidMain/kotlin",
                        "src/androidTest/kotlin",
                        "src/iosMain/kotlin",
                        "src/iosTest/kotlin",
                    ),
                )
            }

            dependencies {
                "detektPlugins"(libs.findLibrary("detekt.compose").get())
            }

            runBeforePreBuild("detekt")
        }
    }
}
