package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.type.enumeration.DataType.INT
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value

class ModuloFunction : Function("mod") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(ofType(INT), ofType(INT)) body { _, (a, b) ->
         Value.int(a.value as Int % b.value as Int)
      }
   }
}