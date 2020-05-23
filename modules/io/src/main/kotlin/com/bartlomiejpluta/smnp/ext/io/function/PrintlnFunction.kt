package com.bartlomiejpluta.smnp.ext.io.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.vararg
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.anyType
import com.bartlomiejpluta.smnp.type.model.Value

class PrintlnFunction : Function("println") {
    override fun define(new: FunctionDefinitionTool) {
        new function vararg(anyType()) body { _, (vararg) ->
            println((vararg.value as List<Value>).joinToString("") { it.stringify() })
            Value.void()
        }
    }
}