package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.ImportNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.error.EnvironmentException
import io.smnp.error.PositionException
import io.smnp.error.SmnpException
import io.smnp.evaluation.model.entity.EvaluatorOutput

class ImportEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(ImportNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val path = (node as ImportNode).path.joinToString(".") { (it as IdentifierNode).token.rawValue }

        try {
            environment.loadModule(path)
        } catch(e: SmnpException) {
            throw PositionException(EnvironmentException(e, environment), node.position)
        }

        return EvaluatorOutput.ok()
    }
}