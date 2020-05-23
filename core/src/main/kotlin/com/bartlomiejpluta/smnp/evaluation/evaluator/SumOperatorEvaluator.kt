package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.data.entity.Note
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.SumOperatorNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.TokenNode
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.ShouldNeverReachThisLineException
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.evaluation.util.NumberUnification.unify
import com.bartlomiejpluta.smnp.math.Fraction
import com.bartlomiejpluta.smnp.type.enumeration.DataType.*
import com.bartlomiejpluta.smnp.type.model.Value
import com.bartlomiejpluta.smnp.type.model.Value.Companion.list
import com.bartlomiejpluta.smnp.type.model.Value.Companion.note
import com.bartlomiejpluta.smnp.type.model.Value.Companion.string

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