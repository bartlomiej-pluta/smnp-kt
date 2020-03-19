package io.smnp.ext.lang.method

import io.smnp.callable.method.Method
import io.smnp.callable.method.MethodDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.type.matcher.Matcher.Companion.anyType
import io.smnp.type.model.Value

class StringifyMethod : Method(anyType(), "toString") {
   override fun define(new: MethodDefinitionTool) {
      new method simple() body { _, obj, _ ->
         Value.string(obj.stringify())
      }
   }
}