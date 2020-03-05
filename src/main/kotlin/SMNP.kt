import interpreter.Interpreter

fun main(args: Array<String>) {
    val interpreter = Interpreter()
    interpreter.run("{ return 2; 2*2; { 3+3; 1*2**3.@c:14 == (2 > not false) } throw \"Hello, world\" }")
}