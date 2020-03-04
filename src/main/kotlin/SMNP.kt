import dsl.token.tokenizer.DefaultTokenizer
import interpreter.Interpreter
import java.io.File

fun main(args: Array<String>) {
    val interpreter = Interpreter()
    interpreter.run(File("/home/bartek/Developent/SMNP-Kotlin/examples/adeste.mus"))
}