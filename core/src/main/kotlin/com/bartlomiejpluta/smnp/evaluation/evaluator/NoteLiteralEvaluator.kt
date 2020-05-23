package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.data.entity.Note
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.NoteLiteralNode
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.type.model.Value

class NoteLiteralEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(NoteLiteralNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as NoteLiteralNode).token.value as Note
        return EvaluatorOutput.value(Value.note(value))
    }
}