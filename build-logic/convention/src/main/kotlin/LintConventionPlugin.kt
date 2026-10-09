import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import com.android.build.gradle.LintPlugin
import io.bifri.interview.cmp.padel.findPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
internal class LintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            when {
                pluginManager.hasPlugin(findPluginId("android.application")) ->
                    configure<ApplicationExtension> { lint(Lint::configure) }

                pluginManager.hasPlugin("com.android.library") ->
                    configure<LibraryExtension> { lint(Lint::configure) }

                else -> {
                    apply<LintPlugin>()
                    extensions.configure<Lint>(Lint::configure)
                }
            }
        }
    }
}

private fun Lint.configure() {
    abortOnError = true
    checkDependencies = true
    disable += "GradleDependency"
    xmlReport = true
}
