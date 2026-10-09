import io.bifri.interview.cmp.padel.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class BignumConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            extensions.configure<KotlinMultiplatformExtension>(::configureBignum)
        }
    }
}

private fun Project.configureBignum(extension: KotlinMultiplatformExtension) {
    extension.apply {
        sourceSets {
            commonMain.dependencies {
                implementation(libs.findLibrary("bignum").get())
                implementation(libs.findLibrary("bignum.serialization").get())
            }
        }
    }
}
