package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.evaluation.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class DefaultEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(Node::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        return oneOf(
            ExpressionEvaluator()
        ).evaluate(node, environment)
    }
}