import interpreter.Interpreter

fun main(args: Array<String>) {
    val interpreter = Interpreter()
    interpreter.run("extend list<note, int> as this { function play() { play(this); return null; } function x() {} }")
}