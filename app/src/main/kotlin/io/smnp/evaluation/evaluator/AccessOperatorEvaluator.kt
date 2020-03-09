package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.AccessOperatorNode
import io.smnp.dsl.ast.model.node.FunctionCallNode
import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.error.EvaluationException
import io.smnp.evaluation.model.entity.EvaluatorOutput

class AccessOperatorEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(AccessOperatorNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val evaluator = ExpressionEvaluator()
        val (lhsNode, _, rhsNode) = (node as AccessOperatorNode)
        val lhs = evaluator.evaluate(lhsNode, environment).value!!

        return when (rhsNode) {
            is IdentifierNode -> {
                val rhs = rhsNode.token.rawValue
                EvaluatorOutput.value(lhs.properties[rhs] ?: throw EvaluationException("Unknown property $rhs of type ${lhs.type.name.toLowerCase()}", rhsNode.position))
            }
            is FunctionCallNode -> {
                // todo Implement when methods become available
                EvaluatorOutput.fail()
            }
            else -> {
                throw EvaluationException("Invalid property access type - only property name and method call are allowed", rhsNode.position)
            }
        }
    }
}