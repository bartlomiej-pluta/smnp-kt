package io.smnp.evaluation.evaluator

import io.smnp.data.entity.Note
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.SumOperatorNode
import io.smnp.dsl.ast.model.node.TokenNode
import io.smnp.dsl.token.model.enumeration.TokenType
import io.smnp.environment.Environment
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import io.smnp.evaluation.util.NumberUnification.unify
import io.smnp.math.Fraction
import io.smnp.type.enumeration.DataType.*
import io.smnp.type.model.Value
import io.smnp.type.model.Value.Companion.list
import io.smnp.type.model.Value.Companion.note
import io.smnp.type.model.Value.Companion.string

class SumOperatorEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(SumOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val evaluator = ExpressionEvaluator()
      val (lhsNode, opNode, rhsNode) = (node as SumOperatorNode)
      val lhs = evaluator.evaluate(lhsNode, environment).value
      val rhs = evaluator.evaluate(rhsNode, environment).value
      val operator = (opNode as TokenNode).token.type

      return EvaluatorOutput.value(
         when (operator) {
            TokenType.PLUS -> plus(lhs, opNode, rhs, environment)
            TokenType.MINUS -> minus(lhs, opNode, rhs, environment)
            else -> throw ShouldNeverReachThisLineException()
         }
      )
   }

   private fun plus(lhs: Value, plusNode: Node, rhs: Value, environment: Environment): Value {
      return when {
         areNumeric(lhs, rhs) -> unify(
            lhs,
            rhs,
            int = { (l, r) -> Value.int(l + r) },
            float = { (l, r) -> Value.float(l + r) })
         lhs.type == STRING -> string(lhs.value as String + rhs.value.toString())
         areLists(lhs, rhs) -> list(lhs.value as List<Value> + rhs.value as List<Value>)
         lhs.type == NOTE && rhs.type == INT -> note(lhs.value as Note + Fraction(1, rhs.value as Int))
         else -> throw contextEvaluationException(
            "The ${lhs.typeName} and ${rhs.typeName} are not supported by + operator",
            plusNode.position,
            environment
         )
      }
   }

   private fun minus(lhs: Value, minusNode: Node, rhs: Value, environment: Environment): Value {
      return if (areNumeric(lhs, rhs))
         unify(
            lhs,
            rhs,
            int = { (l, r) -> Value.int(l - r) },
            float = { (l, r) -> Value.float(l - r) }
         )
      else throw contextEvaluationException(
         "The - operator supports only numeric values",
         minusNode.position,
         environment
      )
   }

   private fun areNumeric(lhs: Value, rhs: Value) = lhs.type.isNumeric() && rhs.type.isNumeric()
   private fun areLists(lhs: Value, rhs: Value) = lhs.type == LIST && rhs.type == LIST
}