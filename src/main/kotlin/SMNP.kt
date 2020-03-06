import interpreter.Interpreter

fun main(args: Array<String>) {
    val interpreter = Interpreter()
    interpreter.run("{ a = b = c = [2, 3, 4] as (i, j, k) ^ { x y z(1, 2, c(), {a  -> b() }) } % i == 2 [1, xyz, [true, { 1 -> false, 2 -> { @c -> @d, @e -> false } }, [@c], 4, \"h\"]] 2 @c @d * 3.14 }")
}