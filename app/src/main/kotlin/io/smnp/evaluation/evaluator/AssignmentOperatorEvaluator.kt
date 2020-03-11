package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.AssignmentOperatorNode
import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class AssignmentOperatorEvaluator : Evaluator() {
    private val evaluator = ExpressionEvaluator()

    override fun supportedNodes() = listOf(AssignmentOperatorNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val (identifierNode, _, valueNode) = node as AssignmentOperatorNode
        val identifier = (identifierNode as IdentifierNode).token.rawValue
        val value = evaluator.evaluate(valueNode, environment).value!!

        environment.setVariable(identifier, value)

        return EvaluatorOutput.value(value)
    }
}