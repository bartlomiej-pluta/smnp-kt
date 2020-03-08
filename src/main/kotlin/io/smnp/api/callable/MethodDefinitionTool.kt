package io.smnp.api.callable

import io.smnp.api.environment.Environment
import io.smnp.api.model.Value
import io.smnp.api.signature.Signature

class MethodDefinitionTool {
    val definitions: MutableList<MethodDefinition> = mutableListOf()

    infix fun method(signature: Signature): MethodDefinitionToolStage2 {
        return MethodDefinitionToolStage2(signature)
    }

    inner class MethodDefinitionToolStage2(private val signature: Signature) {
        infix fun define(body: (Environment, Value, List<Value>) -> Value) {
            definitions.add(MethodDefinition(signature, body))
        }
    }

}
