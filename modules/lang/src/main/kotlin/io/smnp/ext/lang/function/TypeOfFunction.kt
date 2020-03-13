package io.smnp.ext.lang.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.type.matcher.Matcher.Companion.allTypes
import io.smnp.type.model.Value

class TypeOfFunction : Function("typeOf") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(allTypes()) body { _, (obj) ->
         Value.string(obj.type.toString()) // TODO
      }
   }
}