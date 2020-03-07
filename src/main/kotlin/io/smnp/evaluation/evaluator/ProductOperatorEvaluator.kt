package io.smnp.evaluation.evaluator

import io.smnp.data.enumeration.DataType
import io.smnp.data.model.Value
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.ProductOperatorNode
import io.smnp.dsl.ast.model.node.TokenNode
import io.smnp.dsl.token.model.enumeration.TokenType
import io.smnp.error.EvaluationException
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.evaluation.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class ProductOperatorEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(ProductOperatorNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val evaluator = DefaultEvaluator()
        val (lhsNode, opNode, rhsNode) = (node as ProductOperatorNode)
        val lhs = evaluator.evaluate(lhsNode, environment).value!!
        val rhs = evaluator.evaluate(rhsNode, environment).value!!
        val operator = (opNode as TokenNode).token.type

        if (!lhs.type.isNumeric() || !rhs.type.isNumeric()) {
            throw EvaluationException("Operator $operator supports only numeric types", node.position)
        }

        return EvaluatorOutput.value(
            when (operator) {
                TokenType.ASTERISK -> product(lhs, rhs)
                TokenType.SLASH -> quotient(lhs, rhs)
                else -> throw ShouldNeverReachThisLineException()
            }
        )
    }

    private fun product(lhs: Value, rhs: Value): Value {
        if(listOf(lhs.type, rhs.type).contains(DataType.FLOAT)) {
            return Value.float((lhs.value as Number).toFloat() * (rhs.value as Number).toFloat())
        }

        return Value.int((lhs.value as Int) * (rhs.value as Int))
    }

    private fun quotient(lhs: Value, rhs: Value): Value {
        if(listOf(lhs.type, rhs.type).contains(DataType.FLOAT)) {
            return Value.float((lhs.value as Number).toFloat() / (rhs.value as Number).toFloat())
        }

        return Value.int((lhs.value as Int) / (rhs.value as Int))
    }
}