package at.neon

import at.neon.KIRVizConfiguration.enabledCompilerConfigurationKey
import at.neon.KIRVizConfiguration.printDefaultConfiguration
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.messageCollector

/*
Registrar to register all registrars.
It is found via a ServiceLoader.
Thus, we need an entry in META-INF/services/org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
that reads at.ssw.compilerplugin.PerfMeasureComponentRegistrar
 */
@OptIn(ExperimentalCompilerApi::class)
class KIRVizComponentRegistrar : CompilerPluginRegistrar() {
    override val supportsK2: Boolean = true

    init {
        println("... init at.neon.KIRVizComponentRegistrar ...")
    }

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        println("... registerExtensions in at.neon.KIRVizComponentRegistrar ...")
        if (configuration[enabledCompilerConfigurationKey] == false) {
            return
        }

        configuration.printDefaultConfiguration()

        val messageCollector = configuration.messageCollector

        // Frontend plugin registrar
        /*
        FirExtensionRegistrarAdapter.registerExtension(
            PerfMeasureExtensionRegistrar(
                configuration[LOG_ANNOTATION_KEY] ?: listOf()
            )
        )
        */

        // Backend plugin
        IrGenerationExtension.registerExtension(KIRVizIrGenerationExtension(messageCollector))
    }
}