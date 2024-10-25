package at.neon

import at.neon.KIRVizConfiguration.cliOptions
import at.neon.KIRVizConfiguration.enabledCompilerConfigurationKey
import at.neon.KIRVizConfiguration.enabledOptionName
import org.jetbrains.kotlin.compiler.plugin.*
import org.jetbrains.kotlin.config.CompilerConfiguration

/*
Commandline processor to process options.
This is the entry point for the compiler plugin.
It is found via a ServiceLoader.
Thus, we need an entry in META-INF/services/org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
that reads at.ssw.compilerplugin.KPerfMeasureCommandLineProcessor
 */
@OptIn(ExperimentalCompilerApi::class)
class KIRVizCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String = "k-ir-viz-kotlin-compiler-plugin"
    override val pluginOptions: Collection<CliOption> = cliOptions

    init {
        println("... init at.neon.KIRVizCommandLineProcessor ...")
    }

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) {
        println("... at.neon.KIRVizCommandLineProcessor processes option ($option, $value) ...")
        when (option.optionName) {
            enabledOptionName -> configuration.put(enabledCompilerConfigurationKey, value.toBoolean())
            else -> throw CliOptionProcessingException("KPerfMeasureCommandLineProcessor.processOption encountered unknown CLI compiler plugin option: ${option.optionName}")
        }
    }
}