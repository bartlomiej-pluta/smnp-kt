package com.bartlomiejpluta.smnp.ext.lang.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.anyType
import com.bartlomiejpluta.smnp.type.model.Value

class TypeOfFunction : Function("typeOf") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(anyType()) body { _, (obj) ->
         Value.string(obj.typeName)
      }
   }
}