package io.smnp.evaluation.evaluator

import io.smnp.data.model.Value
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.StringLiteralNode
import io.smnp.evaluation.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class StringLiteralEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(StringLiteralNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as StringLiteralNode).token.value as String
        return EvaluatorOutput.value(Value.string(value))
    }
}