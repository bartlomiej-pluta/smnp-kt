import interpreter.Interpreter

fun main(args: Array<String>) {
    val interpreter = Interpreter()
    interpreter.run("true 123 -\"fsfsef\".13.15 @c:14 3.14")
}