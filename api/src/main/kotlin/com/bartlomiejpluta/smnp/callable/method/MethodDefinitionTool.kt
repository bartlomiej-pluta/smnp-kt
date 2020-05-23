package com.bartlomiejpluta.smnp.callable.method

import com.bartlomiejpluta.smnp.callable.signature.Signature
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.type.model.Value

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
