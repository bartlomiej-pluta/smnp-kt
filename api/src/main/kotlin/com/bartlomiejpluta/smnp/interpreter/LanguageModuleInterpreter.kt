package com.bartlomiejpluta.smnp.interpreter

import com.bartlomiejpluta.smnp.environment.Environment

interface LanguageModuleInterpreter {
   fun run(code: String, source: String): Environment
}