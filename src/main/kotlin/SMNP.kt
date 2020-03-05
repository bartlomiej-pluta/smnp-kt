import interpreter.Interpreter

fun main(args: Array<String>) {
    val interpreter = Interpreter()
    interpreter.run("2 + 2 * 2 / 2 ** 2")
}