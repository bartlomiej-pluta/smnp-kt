package io.smnp.evaluation.evaluator

import io.smnp.data.entity.Note
import io.smnp.api.model.Value
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.NoteLiteralNode
import io.smnp.api.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class NoteLiteralEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(NoteLiteralNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as NoteLiteralNode).token.value as Note
        return EvaluatorOutput.value(Value.note(value))
    }
}