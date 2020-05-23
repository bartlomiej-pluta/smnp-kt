package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.IntegerLiteralNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.type.model.Value

class IntegerLiteralEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(IntegerLiteralNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as IntegerLiteralNode).token.value as Int
        return EvaluatorOutput.value(Value.int(value))
    }
}