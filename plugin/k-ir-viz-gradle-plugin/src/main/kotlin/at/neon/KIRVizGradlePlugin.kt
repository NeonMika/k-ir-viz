package at.neon

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

// KotlinCompilerPluginSupportPlugin inherits from Plugin<Project>
class KIRVizGradlePlugin : KotlinCompilerPluginSupportPlugin {
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true

    override fun apply(target: Project) {
        // Could be overridden
        super.apply(target)

        target.extensions.add("KIRViz", KIRVizGradleExtension())

        println("# applying KIRVizGradlePlugin to Project ${target.name}")
    }

    override fun applyToCompilation(
        kotlinCompilation: KotlinCompilation<*>
    ): Provider<List<SubpluginOption>> {
        println("## applying KIRVizGradlePlugin to KotlinCompilation[name=${kotlinCompilation.name}, target=${kotlinCompilation.target}, compilationName=${kotlinCompilation.compilationName}, platformType=${kotlinCompilation.platformType})")

        val project = kotlinCompilation.target.project

        return project.provider {
            // internally the parameters now defined here in applyToCompilation are passed as
            // "-P plugin:<compilerPluginId>:<key>=<value>" on the command line
            // these values can then be accessed in the Kotlin compiler plugin

            val ext = project.extensions.getByName("KIRViz") as KIRVizGradleExtension

            val settings = listOf(
                SubpluginOption("enabled", ext.enabled.toString())
            )

            println("### KIRVizGradlePlugin.applyToCompilation: settings=[${settings.joinToString { "${it.key}=${it.value}" }}]")

            return@provider settings
        }
    }

    // must be the same as "override val pluginId" in compiler plugin
    // based on this id the command line parameters will be passed to the compiler
    //
    // internally the parameters defined in applyToCompilation are passed as
    // "-P plugin:<compilerPluginId>:<key>=<value>" on the command line
    // (see https://kotlinlang.org/docs/all-open-plugin.html#command-line-compiler, see "The plugin option format is...")
    override fun getCompilerPluginId(): String {
        return "k-ir-viz-kotlin-compiler-plugin"
    }

    // the name of the Gradle project that contains the compiler plugin
    // this will be looked up on maven
    override fun getPluginArtifact(): SubpluginArtifact =
        SubpluginArtifact(groupId = "at.neon", artifactId = "k-ir-viz-compiler-plugin", version = "0.0.1")
}
