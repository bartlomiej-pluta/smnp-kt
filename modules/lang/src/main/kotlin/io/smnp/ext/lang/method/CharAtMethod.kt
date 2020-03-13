package io.smnp.ext.lang.method

import io.smnp.callable.method.Method
import io.smnp.callable.method.MethodDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.error.RuntimeException
import io.smnp.type.enumeration.DataType.INT
import io.smnp.type.enumeration.DataType.STRING
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value

class CharAtMethod : Method(ofType(STRING),"charAt") {
   override fun define(new: MethodDefinitionTool) {
      new method simple(ofType(INT)) body { _, obj, (index) ->
         Value.string((obj.value!! as String).getOrNull(index.value!! as Int)?.toString() ?: throw RuntimeException("Index '${index.value!!}' runs out of string bounds"))
      }
   }
}