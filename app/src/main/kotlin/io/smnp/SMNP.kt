package io.smnp

import io.smnp.interpreter.DefaultInterpreter
import java.io.File;

fun main(args: Array<String>) {
   val interpreter = DefaultInterpreter()
   interpreter.run(File("/home/bartek/Developent/SMNP-Kotlin/examples/scratchpad.mus"))
}