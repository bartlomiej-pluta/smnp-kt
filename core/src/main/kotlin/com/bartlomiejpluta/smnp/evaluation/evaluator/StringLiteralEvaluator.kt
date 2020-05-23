package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.StringLiteralNode
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.type.model.Value

class StringLiteralEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(StringLiteralNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as StringLiteralNode).token.value as String
        return EvaluatorOutput.value(Value.string(value))
    }
}