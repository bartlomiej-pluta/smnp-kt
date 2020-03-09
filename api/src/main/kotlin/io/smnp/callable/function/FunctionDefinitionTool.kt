package io.smnp.callable.function

import io.smnp.callable.signature.Signature
import io.smnp.environment.Environment
import io.smnp.type.model.Value

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
