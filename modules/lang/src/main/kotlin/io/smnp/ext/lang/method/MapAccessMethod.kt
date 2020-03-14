package io.smnp.ext.lang.method

import io.smnp.callable.method.Method
import io.smnp.callable.method.MethodDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.error.EvaluationException
import io.smnp.type.enumeration.DataType.MAP
import io.smnp.type.matcher.Matcher.Companion.anyType
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value

class MapAccessMethod : Method(ofType(MAP), "get") {
    override fun define(new: MethodDefinitionTool) {
        new method simple(anyType()) body { _, obj, (key) ->
            val map = (obj.value as Map<Value, Value>)
            map[key] ?: throw EvaluationException("Key '${key.value}' not found")
        }
    }
}