import at.neon.KIRVizComponentRegistrar
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KIRVizTests {

    @OptIn(ExperimentalCompilerApi::class)
    @Test
    fun `test say hello`() {
        val source = SourceFile.kotlin(
            "main.kt",
            """
                    fun main() {
                      sayHello()
                      sayHello("Hi", "there")
                    }

                    fun sayHello(greeting: String = "Hello", name: String = "World") {
                        val result = "${'$'}greeting, ${'$'}name!"
                        println(result)
                    }
                    """
        )

        val result = compile(
            source,
            KIRVizComponentRegistrar()
        )
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        result.main()
    }
}


