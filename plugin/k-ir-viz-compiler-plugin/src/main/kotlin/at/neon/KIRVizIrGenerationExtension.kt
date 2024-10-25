package at.neon

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.nameWithPackage
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import kotlin.time.ExperimentalTime

/*
Backend plugin
 */
class KIRVizIrGenerationExtension(
    private val messageCollector: MessageCollector
) : IrGenerationExtension {
    @OptIn(UnsafeDuringIrConstructionAPI::class, ExperimentalTime::class)
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        messageCollector.report(CompilerMessageSeverity.INFO, "# (backend plugin) at.neon.KIRVizIrGenerationExtension.generate")
        println("# (backend plugin) at.neon.KIRVizIrGenerationExtension.generate")

        println("## Files affected by at.neon.KIRVizIrGenerationExtension:")
        moduleFragment.files.forEach {
            println("### ${it.nameWithPackage}")
        }
    }
}