package io.smnp.evaluation.evaluator

import io.smnp.data.model.Value
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.NotOperatorNode
import io.smnp.evaluation.environment.Environment
import io.smnp.evaluation.evaluator.Evaluator.Companion.assert
import io.smnp.evaluation.model.entity.EvaluatorOutput

class NotOperatorEvaluator : Evaluator {
    override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
        val evaluator = BoolLiteralEvaluator()
        val (_, operandNode) = (node as NotOperatorNode)
        val operand = assert(evaluator, "bool").evaluate(operandNode, environment)

        return EvaluatorOutput.value(Value.bool(!(operand.value!!.value as Boolean)))
    }
}