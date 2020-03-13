package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.error.EnvironmentException
import io.smnp.error.EvaluationException
import io.smnp.error.PositionException
import io.smnp.evaluation.model.entity.EvaluatorOutput

class IdentifierEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(IdentifierNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val identifier = (node as IdentifierNode).token.rawValue

        try {
            return EvaluatorOutput.value(environment.getVariable(identifier))
        } catch (e: EvaluationException) {
            throw PositionException(EnvironmentException(e, environment), node.position)
        }
    }
}