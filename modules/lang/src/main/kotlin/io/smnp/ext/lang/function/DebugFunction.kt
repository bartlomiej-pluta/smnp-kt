package io.smnp.ext.lang.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.type.model.Value

class DebugFunction : Function("debug") {
    override fun define(new: FunctionDefinitionTool) {
        new function simple() define { env, _ ->
            env.printCallStack()
            Value.void()
        }
    }
}