import io.bifri.interview.cmp.padel.libs
import io.bifri.interview.cmp.padel.runBeforePreBuild
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

@Suppress("unused")
internal class KtLintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<KtlintPlugin>()

            extensions.configure<KtlintExtension> {
                val ktlintVersion = libs.findVersion("ktlint").get().requiredVersion
                version.set(ktlintVersion)
                android.set(true)
                baseline.set(file("$rootDir/config/ktlint-baseline.xml"))
                reporters {
                    reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
                    reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
                }
                verbose.set(true)
            }

            runBeforePreBuild("ktlintCheck")
        }
    }
}
