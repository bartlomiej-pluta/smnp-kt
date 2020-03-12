package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.PowerOperatorNode
import io.smnp.environment.Environment
import io.smnp.error.EvaluationException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.type.model.Value
import kotlin.math.pow

class PowerOperatorEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(PowerOperatorNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val (lhsNode, _, rhsNode) = (node as PowerOperatorNode)
        val evaluator = ExpressionEvaluator()
        val lhs = evaluator.evaluate(lhsNode, environment).value!!
        val rhs = evaluator.evaluate(rhsNode, environment).value!!

        if(!lhs.type.isNumeric() || !rhs.type.isNumeric()) {
            throw EvaluationException("Operator ** supports only numeric types", node.position)
        }

        return EvaluatorOutput.value(Value.float((lhs.value as Number).toFloat().pow((rhs.value as Number).toFloat())))
    }
}