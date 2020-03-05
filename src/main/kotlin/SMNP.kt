import interpreter.Interpreter

fun main(args: Array<String>) {
    val interpreter = Interpreter()
    interpreter.run("true 123 \"fsfsef\" @c:14 3.14")
}