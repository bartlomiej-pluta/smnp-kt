import interpreter.Interpreter

fun main(args: Array<String>) {
    val interpreter = Interpreter()
    interpreter.run("2 ** 2 * true ** 123 ** 4 -\"fsfsef\".13.15 @c:14 3.14")
}