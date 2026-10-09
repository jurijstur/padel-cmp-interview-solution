import com.android.build.api.dsl.LibraryExtension
import io.bifri.interview.cmp.padel.configureAndroid
import io.bifri.interview.cmp.padel.configureKmp
import io.bifri.interview.cmp.padel.findPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class LibraryCoreConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(findPluginId("kotlin.multiplatform"))
                apply(findPluginId("android.library"))
                apply(findPluginId("kotlin.serialization"))
                apply(findPluginId("convention.detekt"))
                apply(findPluginId("convention.ktlint"))
                apply(findPluginId("convention.lint"))
                apply(findPluginId("convention.bignum"))
                apply(findPluginId("convention.filekit"))
                apply(findPluginId("convention.koin"))
            }

            extensions.configure<KotlinMultiplatformExtension>(::configureKmp)
            extensions.configure<LibraryExtension>(::configureAndroid)
        }
    }
}
