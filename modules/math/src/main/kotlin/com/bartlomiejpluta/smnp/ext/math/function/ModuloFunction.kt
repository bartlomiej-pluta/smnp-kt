package com.bartlomiejpluta.smnp.ext.math.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.type.enumeration.DataType.INT
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.model.Value

class ModuloFunction : Function("mod") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(ofType(INT), ofType(INT)) body { _, (a, b) ->
         Value.int(a.value as Int % b.value as Int)
      }
   }
}