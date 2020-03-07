package io.smnp.evaluation.evaluator

import io.smnp.data.enumeration.DataType
import io.smnp.data.model.Value
import io.smnp.dsl.ast.model.node.MinusOperatorNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.error.EvaluationException
import io.smnp.evaluation.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class MinusOperatorEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(MinusOperatorNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val evaluator = DefaultEvaluator()
        val (_, operandNode) = (node as MinusOperatorNode)
        val operand = evaluator.evaluate(operandNode, environment)

        return EvaluatorOutput.value(when(operand.value!!.type) {
            DataType.INT -> Value.int(-1 * operand.value.value as Int)
            DataType.FLOAT -> Value.float(-1.0f * operand.value.value as Float)
            DataType.STRING -> Value.string((operand.value.value as String).reversed())
            DataType.LIST -> Value.list((operand.value.value as List<Value>).reversed())
            else -> throw EvaluationException("Type ${operand.value.type.name.toLowerCase()} does not support minus operator", node.position)
        })
    }
}