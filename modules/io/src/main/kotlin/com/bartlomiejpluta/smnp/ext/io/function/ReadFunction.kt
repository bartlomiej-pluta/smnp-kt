package com.bartlomiejpluta.smnp.ext.io.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.type.enumeration.DataType.STRING
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.optional
import com.bartlomiejpluta.smnp.type.model.Value

class ReadFunction : Function("read") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(optional(ofType(STRING))) body { _, arguments ->
         arguments.getOrNull(0)?.let { print(it.value) }
         Value.string(readLine() ?: "")
      }
   }
}