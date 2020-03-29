package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.RootNode
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class RootEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(RootNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        return repeat(assert(oneOf(
            ImportEvaluator(),
            ExpressionEvaluator(),
            FunctionDefinitionEvaluator(),
            ExtendEvaluator(),
            DefaultEvaluator()
        ), "correct statement")).evaluate(node, environment).let {
            environment.dispose()
            it
        }
    }
}