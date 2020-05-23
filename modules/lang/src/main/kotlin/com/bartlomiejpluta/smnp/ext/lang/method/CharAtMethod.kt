package com.bartlomiejpluta.smnp.ext.lang.method

import com.bartlomiejpluta.smnp.callable.method.Method
import com.bartlomiejpluta.smnp.callable.method.MethodDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.error.EvaluationException
import com.bartlomiejpluta.smnp.type.enumeration.DataType.INT
import com.bartlomiejpluta.smnp.type.enumeration.DataType.STRING
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.model.Value

class CharAtMethod : Method(ofType(STRING),"charAt") {
   override fun define(new: MethodDefinitionTool) {
      new method simple(ofType(INT)) body { _, obj, (index) ->
         Value.string((obj.value as String).getOrNull(index.value as Int)?.toString() ?: throw EvaluationException("Index '${index.value}' runs out of string bounds"))
      }
   }
}