package io.smnp.ext.io.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.type.enumeration.DataType.STRING
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.matcher.Matcher.Companion.optional
import io.smnp.type.model.Value

class ReadFunction : Function("read") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(optional(ofType(STRING))) body { _, arguments ->
         arguments.getOrNull(0)?.let { print(it.value!!) }
         Value.string(readLine() ?: "")
      }
   }
}