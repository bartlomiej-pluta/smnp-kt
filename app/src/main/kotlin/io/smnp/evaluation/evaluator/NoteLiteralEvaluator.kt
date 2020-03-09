package io.smnp.evaluation.evaluator

import io.smnp.data.entity.Note
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.NoteLiteralNode
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.type.model.Value

class NoteLiteralEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(NoteLiteralNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as NoteLiteralNode).token.value as Note
        return EvaluatorOutput.value(Value.note(value))
    }
}