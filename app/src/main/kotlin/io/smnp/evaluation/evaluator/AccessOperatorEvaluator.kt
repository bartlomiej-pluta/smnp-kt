package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.*
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
                val (identifierNode, argsNode) = rhsNode
                val identifier = (identifierNode as IdentifierNode).token.rawValue
                val arguments = (argsNode as FunctionCallArgumentsNode).items.map { evaluator.evaluate(it, environment).value!! }
                return EvaluatorOutput.value(environment.invokeMethod(lhs, identifier, arguments))
            }
            else -> {
                throw EvaluationException("Invalid property access type - only property name and method call are allowed", rhsNode.position)
            }
        }
    }
}