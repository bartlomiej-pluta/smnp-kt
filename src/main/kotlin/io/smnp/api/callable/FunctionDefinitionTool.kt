package io.smnp.api.callable

import io.smnp.api.environment.Environment
import io.smnp.api.model.Value
import io.smnp.api.signature.Signature

class FunctionDefinitionTool {
    val definitions: MutableList<FunctionDefinition> = mutableListOf()

    infix fun function(signature: Signature): FunctionDefinitionToolStage2 {
        return FunctionDefinitionToolStage2(signature)
    }

    inner class FunctionDefinitionToolStage2(private val signature: Signature) {
        infix fun define(body: (Environment, List<Value>) -> Value) {
            definitions.add(FunctionDefinition(signature, body))
        }
    }

}
