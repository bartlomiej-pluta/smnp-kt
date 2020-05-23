package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput

class DefaultEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(Node::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        return oneOf(
            ConditionEvaluator(),
            BlockEvaluator(true),
            ThrowEvaluator(),
            ReturnEvaluator(),
            ExpressionEvaluator()
        ).evaluate(node, environment)
    }
}