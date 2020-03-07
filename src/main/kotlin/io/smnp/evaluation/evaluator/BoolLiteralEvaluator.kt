package io.smnp.evaluation.evaluator

import io.smnp.api.model.Value
import io.smnp.dsl.ast.model.node.BoolLiteralNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.api.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class BoolLiteralEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(BoolLiteralNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as BoolLiteralNode).token.value as Boolean
        return EvaluatorOutput.value(Value.bool(value))
    }
}