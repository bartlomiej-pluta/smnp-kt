package io.smnp.interpreter

import io.smnp.environment.Environment

interface Interpreter {
   fun run(code: String): Environment
}