package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.type.enumeration.DataType.INT
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value

class SleepFunction : Function("sleep") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(ofType(INT)) body { _, (milli) ->
         Thread.sleep((milli.value as Int).toLong())
         Value.void()
      }
   }
}