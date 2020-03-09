package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.NotOperatorNode
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.type.model.Value

class NotOperatorEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(NotOperatorNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val evaluator = BoolLiteralEvaluator()
        val (_, operandNode) = (node as NotOperatorNode)
        val operand = assert(evaluator, "bool").evaluate(operandNode, environment)

        return EvaluatorOutput.value(Value.bool(!(operand.value!!.value as Boolean)))
    }
}