package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.type.enumeration.DataType.INT
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.matcher.Matcher.Companion.optional
import io.smnp.type.model.Value
import kotlin.system.exitProcess

class ExitFunction : Function("exit") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(optional(ofType(INT))) body { _, arguments ->
         val exitCode = arguments.getOrNull(0) ?: Value.int(0)
         exitProcess(exitCode.value as Int)
      }
   }
}