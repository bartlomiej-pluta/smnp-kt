package io.smnp.callable.method

import io.smnp.callable.signature.Signature
import io.smnp.environment.Environment
import io.smnp.type.model.Value

class MethodDefinitionTool {
    val definitions: MutableList<MethodDefinition> = mutableListOf()

    infix fun method(signature: Signature): MethodDefinitionToolStage2 {
        return MethodDefinitionToolStage2(signature)
    }

    inner class MethodDefinitionToolStage2(private val signature: Signature) {
        infix fun body(body: (Environment, Value, List<Value>) -> Value) {
            definitions.add(MethodDefinition(signature, body))
        }
    }

}
