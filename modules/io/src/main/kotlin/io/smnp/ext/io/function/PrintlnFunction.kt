package io.smnp.ext.io.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.vararg
import io.smnp.type.matcher.Matcher.Companion.anyType
import io.smnp.type.model.Value

class PrintlnFunction : Function("println") {
    override fun define(new: FunctionDefinitionTool) {
        new function vararg(anyType()) body { _, (vararg) ->
            println((vararg.value as List<Value>).joinToString("") { it.stringify() })
            Value.void()
        }
    }
}