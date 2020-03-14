package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.ThrowNode
import io.smnp.environment.Environment
import io.smnp.error.CustomException
import io.smnp.evaluation.model.entity.EvaluatorOutput

class ThrowEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(ThrowNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val evaluator = ExpressionEvaluator()
        val valueNode = (node as ThrowNode).value
        val value = evaluator.evaluate(valueNode, environment)

        throw CustomException(value.value.value.toString())
    }
}