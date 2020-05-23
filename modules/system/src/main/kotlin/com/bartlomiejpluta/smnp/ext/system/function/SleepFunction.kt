package com.bartlomiejpluta.smnp.ext.system.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.type.enumeration.DataType.INT
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.model.Value

class SleepFunction : Function("sleep") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(ofType(INT)) body { _, (milli) ->
         Thread.sleep((milli.value as Int).toLong())
         Value.void()
      }
   }
}