import com.android.build.api.dsl.LibraryExtension
import io.bifri.interview.cmp.padel.PadelApiConfig.Companion.fromPadelApiProperties
import io.bifri.interview.cmp.padel.toBuildConfigString
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class PadelApiConfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val padelApiConfig = fromPadelApiProperties()

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    buildConfig = true
                }
                defaultConfig {
                    buildConfigField(
                        type = "String",
                        name = "PadelApiToken",
                        value = padelApiConfig?.apiToken.orEmpty().toBuildConfigString(),
                    )
                }
            }
        }
    }
}
