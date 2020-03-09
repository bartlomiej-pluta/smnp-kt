package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.RelationOperatorNode
import io.smnp.dsl.ast.model.node.TokenNode
import io.smnp.environment.Environment
import io.smnp.error.EvaluationException
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.util.NumberUnification.unify
import io.smnp.type.model.Value

class RelationOperatorEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(RelationOperatorNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val evaluator = ExpressionEvaluator()
        val (lhsNode, opNode, rhsNode) = (node as RelationOperatorNode)
        val lhs = evaluator.evaluate(lhsNode, environment).value!!
        val rhs = evaluator.evaluate(rhsNode, environment).value!!
        val operator = (opNode as TokenNode).token.rawValue

        if (operator in listOf("==", "!=")) {
            return EvaluatorOutput.value(
                Value.bool(
                    if (operator == "==") lhs.value == rhs.value
                    else lhs.value != rhs.value
                )
            )
        }

        if (!lhs.type.isNumeric() || !rhs.type.isNumeric()) {
            throw EvaluationException("Operator $operator supports only numeric types", node.position)
        }

        return EvaluatorOutput.value(
            when(operator) {
                ">" -> unify(lhs, rhs, int = { (l, r) -> Value.bool(l > r) }, float = { (l, r) -> Value.bool(l > r) })
                "<" -> unify(lhs, rhs, int = { (l, r) -> Value.bool(l < r) }, float = { (l, r) -> Value.bool(l < r) })
                ">=" -> unify(lhs, rhs, int = { (l, r) -> Value.bool(l >= r) }, float = { (l, r) -> Value.bool(l >= r) })
                "<=" -> unify(lhs, rhs, int = { (l, r) -> Value.bool(l <= r) }, float = { (l, r) -> Value.bool(l <= r) })
                else -> throw ShouldNeverReachThisLineException()
            }
        )
    }
}