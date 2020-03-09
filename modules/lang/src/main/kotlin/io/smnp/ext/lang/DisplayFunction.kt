package io.smnp.ext.lang

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.vararg
import io.smnp.type.matcher.Matcher.Companion.allTypes
import io.smnp.type.model.Value

class DisplayFunction : Function("println") {
    override fun define(new: FunctionDefinitionTool) {
        new function vararg(allTypes()) define { _, v ->
            println(v.joinToString("") { it.toString() })
            Value.void()
        }
    }
}