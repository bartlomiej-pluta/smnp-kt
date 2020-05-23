package com.bartlomiejpluta.smnp.ext.lang.method

import com.bartlomiejpluta.smnp.callable.method.Method
import com.bartlomiejpluta.smnp.callable.method.MethodDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.error.EvaluationException
import com.bartlomiejpluta.smnp.type.enumeration.DataType.INT
import com.bartlomiejpluta.smnp.type.enumeration.DataType.LIST
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.model.Value

class ListAccessMethod : Method(ofType(LIST), "get") {
    override fun define(new: MethodDefinitionTool) {
        new method simple(ofType(INT)) body { _, value, (index) ->
            val list = value.value as List<Value>
            val i = index.value as Int

            if(i >= list.size) {
                throw EvaluationException("Index '$i' runs out of array bounds")
            }

            list[i]
        }
    }
}