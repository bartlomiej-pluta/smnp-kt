package com.bartlomiejpluta.smnp.ext.lang.constructor

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.type.enumeration.DataType.FLOAT
import com.bartlomiejpluta.smnp.type.enumeration.DataType.INT
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.model.Value

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