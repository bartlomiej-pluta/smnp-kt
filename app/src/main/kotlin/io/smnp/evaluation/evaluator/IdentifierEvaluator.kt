package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class IdentifierEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(IdentifierNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val identifier = (node as IdentifierNode).token.rawValue
        return EvaluatorOutput.value(environment.getVariable(identifier))
    }
}