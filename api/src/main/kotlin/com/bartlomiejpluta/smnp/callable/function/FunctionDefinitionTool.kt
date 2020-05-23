package com.bartlomiejpluta.smnp.callable.function

import com.bartlomiejpluta.smnp.callable.signature.Signature
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.type.model.Value

class FunctionDefinitionTool {
    val definitions: MutableList<FunctionDefinition> = mutableListOf()

    infix fun function(signature: Signature): FunctionDefinitionToolStage2 {
        return FunctionDefinitionToolStage2(signature)
    }

    inner class FunctionDefinitionToolStage2(private val signature: Signature) {
        infix fun body(body: (Environment, List<Value>) -> Value) {
            definitions.add(FunctionDefinition(signature, body))
        }
    }

}
