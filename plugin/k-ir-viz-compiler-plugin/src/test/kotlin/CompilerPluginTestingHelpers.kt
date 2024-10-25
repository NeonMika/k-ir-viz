import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
fun JvmCompilationResult.main() {
    val kClazz = classLoader.loadClass("MainKt")
    val main = kClazz.declaredMethods.single { it.name == "main" && it.parameterCount == 0 }
    main.invoke(null)
}

@OptIn(ExperimentalCompilerApi::class)
fun compile(
    sourceFiles: List<SourceFile>,
    compilerPluginRegistrar: CompilerPluginRegistrar,
    commandLineProcessor: CommandLineProcessor? = null,
    kotlincArguments: List<String> = emptyList(),
): JvmCompilationResult {
    return KotlinCompilation().apply {
        // To have access to dependencies of this project
        inheritClassPath = true

        sources = sourceFiles
        compilerPluginRegistrars = listOf(compilerPluginRegistrar)
        commandLineProcessors = if (commandLineProcessor != null) listOf(commandLineProcessor) else emptyList()
        this.kotlincArguments = kotlincArguments

        // messageOutputStream = System.out
    }.compile()
}

@OptIn(ExperimentalCompilerApi::class)
fun compile(
    sourceFile: SourceFile,
    compilerPluginRegistrar: CompilerPluginRegistrar,
    cliProcessors: CommandLineProcessor? = null,
    kotlincArguments: List<String> = emptyList(),
) = compile(
    listOf(sourceFile),
    compilerPluginRegistrar,
    cliProcessors,
    kotlincArguments
)