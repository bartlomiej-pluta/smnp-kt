package io.smnp.evaluation.evaluator

import io.smnp.api.model.Value
import io.smnp.dsl.ast.model.node.IntegerLiteralNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.api.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class IntegerLiteralEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(IntegerLiteralNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as IntegerLiteralNode).token.value as Int
        return EvaluatorOutput.value(Value.int(value))
    }
}