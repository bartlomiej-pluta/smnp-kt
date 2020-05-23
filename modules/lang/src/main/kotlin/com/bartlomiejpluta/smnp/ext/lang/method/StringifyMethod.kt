package com.bartlomiejpluta.smnp.ext.lang.method

import com.bartlomiejpluta.smnp.callable.method.Method
import com.bartlomiejpluta.smnp.callable.method.MethodDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.anyType
import com.bartlomiejpluta.smnp.type.model.Value

class StringifyMethod : Method(anyType(), "toString") {
   override fun define(new: MethodDefinitionTool) {
      new method simple() body { _, obj, _ ->
         Value.string(obj.stringify())
      }
   }
}