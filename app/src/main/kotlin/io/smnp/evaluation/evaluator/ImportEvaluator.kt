package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.AccessOperatorNode
import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.ImportNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.evaluation.model.entity.EvaluatorOutput

class ImportEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(ImportNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val path = when (val pathNode = (node as ImportNode).path) {
            is AccessOperatorNode -> complexPath(pathNode).joinToString(".")
            is IdentifierNode -> pathNode.token.rawValue
            else -> throw ShouldNeverReachThisLineException()
        }

        environment.loadModule(path)

        return EvaluatorOutput.ok()
    }

    private fun complexPath(segment: AccessOperatorNode): List<String> {
        val segments  = mutableListOf<String>()
        val (lhs, _, rhs) = segment

        segments.add((rhs as IdentifierNode).token.rawValue)

        when(lhs) {
            is AccessOperatorNode -> segments.addAll(complexPath(lhs))
            is IdentifierNode -> segments.add(lhs.token.rawValue)
        }

        return segments.reversed()
    }
}