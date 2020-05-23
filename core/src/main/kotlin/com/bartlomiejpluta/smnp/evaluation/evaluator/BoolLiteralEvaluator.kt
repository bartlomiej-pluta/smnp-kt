package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.BoolLiteralNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.type.model.Value

class BoolLiteralEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(BoolLiteralNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as BoolLiteralNode).token.value as Boolean
        return EvaluatorOutput.value(Value.bool(value))
    }
}