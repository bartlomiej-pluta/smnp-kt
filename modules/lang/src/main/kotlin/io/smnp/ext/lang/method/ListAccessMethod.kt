package io.smnp.ext.lang.method

import io.smnp.callable.method.Method
import io.smnp.callable.method.MethodDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.error.RuntimeException
import io.smnp.type.enumeration.DataType.INT
import io.smnp.type.enumeration.DataType.LIST
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value

class ListAccessMethod : Method(ofType(LIST), "get") {
    override fun define(new: MethodDefinitionTool) {
        new method simple(ofType(INT)) body { _, value, (index) ->
            val list = value.value!! as List<Value>
            val i = index.value!! as Int

            if(i >= list.size) {
                throw RuntimeException("Index '$i' runs out of array bounds")
            }

            list[i]
        }
    }
}