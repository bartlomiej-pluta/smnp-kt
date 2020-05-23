package com.bartlomiejpluta.smnp.ext.lang.method

import com.bartlomiejpluta.smnp.callable.method.Method
import com.bartlomiejpluta.smnp.callable.method.MethodDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.error.EvaluationException
import com.bartlomiejpluta.smnp.type.enumeration.DataType.MAP
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.anyType
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.model.Value

class MapAccessMethod : Method(ofType(MAP), "get") {
    override fun define(new: MethodDefinitionTool) {
        new method simple(anyType()) body { _, obj, (key) ->
            val map = (obj.value as Map<Value, Value>)
            map[key] ?: throw EvaluationException("Key '${key.value}' not found")
        }
    }
}