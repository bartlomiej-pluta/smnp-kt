package io.smnp.evaluation.evaluator

import io.smnp.callable.function.CustomFunction
import io.smnp.dsl.ast.model.node.FunctionDefinitionNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class FunctionDefinitionEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(FunctionDefinitionNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val function = CustomFunction.create(node as FunctionDefinitionNode)
        environment.defineFunction(function)
        return EvaluatorOutput.ok()
    }
}