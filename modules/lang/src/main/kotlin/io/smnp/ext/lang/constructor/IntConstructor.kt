package io.smnp.ext.lang.constructor

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.type.enumeration.DataType.FLOAT
import io.smnp.type.enumeration.DataType.INT
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value

class IntConstructor : Function("Int") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(ofType(INT)) body { _, (int) ->
         int
      }

      new function simple(ofType(FLOAT)) body { _, (float) ->
         Value.int((float.value as Float).toInt())
      }
   }
}