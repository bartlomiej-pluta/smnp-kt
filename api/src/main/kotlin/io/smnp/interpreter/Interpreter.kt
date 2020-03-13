package io.smnp.interpreter

import io.smnp.type.module.Module

interface Interpreter {
   fun run(code: String)
   fun updateRootModule(newRootModule: Module): Unit = throw RuntimeException("Replacing root module is not supported in this Interpreter implementation")
}