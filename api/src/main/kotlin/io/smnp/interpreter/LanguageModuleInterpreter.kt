package io.smnp.interpreter

import io.smnp.environment.Environment

interface LanguageModuleInterpreter {
   fun run(code: String, source: String): Environment
}