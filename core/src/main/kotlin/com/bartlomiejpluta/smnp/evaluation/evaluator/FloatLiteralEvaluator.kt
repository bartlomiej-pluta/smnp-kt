package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.FloatLiteralNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.type.model.Value

class FloatLiteralEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(FloatLiteralNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as FloatLiteralNode).token.value as Float
        return EvaluatorOutput.value(Value.float(value))
    }
}