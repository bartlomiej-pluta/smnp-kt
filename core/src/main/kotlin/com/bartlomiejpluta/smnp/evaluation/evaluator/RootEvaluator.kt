package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.RootNode
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput

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