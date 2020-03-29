package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.FloatLiteralNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.type.model.Value

class FloatLiteralEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(FloatLiteralNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as FloatLiteralNode).token.value as Float
        return EvaluatorOutput.value(Value.float(value))
    }
}