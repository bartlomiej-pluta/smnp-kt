package io.smnp.evaluation.evaluator

import io.smnp.callable.function.CustomFunction
import io.smnp.dsl.ast.model.node.FunctionDefinitionNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.error.EnvironmentException
import io.smnp.error.PositionException
import io.smnp.error.SmnpException
import io.smnp.evaluation.model.entity.EvaluatorOutput

class FunctionDefinitionEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(FunctionDefinitionNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val function = CustomFunction.create(node as FunctionDefinitionNode)

        try {
            environment.defineFunction(function)
        } catch(e: SmnpException) {
            throw PositionException(EnvironmentException(e, environment), node.position)
        }

        return EvaluatorOutput.ok()
    }
}