package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.SumOperatorNode
import io.smnp.dsl.ast.model.node.TokenNode
import io.smnp.dsl.token.model.enumeration.TokenType
import io.smnp.environment.Environment
import io.smnp.error.EnvironmentException
import io.smnp.error.EvaluationException
import io.smnp.error.PositionException
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.util.NumberUnification.unify
import io.smnp.type.enumeration.DataType
import io.smnp.type.model.Value

class SumOperatorEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(SumOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val evaluator = ExpressionEvaluator()
      val (lhsNode, opNode, rhsNode) = (node as SumOperatorNode)
      val lhs = evaluator.evaluate(lhsNode, environment).value!!
      val rhs = evaluator.evaluate(rhsNode, environment).value!!
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
      return if (areNumeric(lhs, rhs))
         unify(lhs, rhs, int = { (l, r) -> Value.int(l + r) }, float = { (l, r) -> Value.float(l + r) })
      else if (lhs.type == DataType.STRING)
         Value.string(lhs.value!! as String + rhs.value.toString())
      else if (areLists(lhs, rhs))
         Value.list(lhs.value!! as List<Value> + rhs.value!! as List<Value>)
      else throw PositionException(
         EnvironmentException(
            EvaluationException(
               "The ${lhs.typeName} and ${rhs.typeName} are not supported by + operator"
            ),
            environment
         ), plusNode.position
      )
   }

   private fun minus(lhs: Value, minusNode: Node, rhs: Value, environment: Environment): Value {
      return if (areNumeric(lhs, rhs))
         unify(
            lhs,
            rhs,
            int = { (l, r) -> Value.int(l - r) },
            float = { (l, r) -> Value.float(l - r) }
         )
      else throw PositionException(
         EnvironmentException(
            EvaluationException("The - operator supports only numeric values"),
            environment
         ),
         minusNode.position
      )
   }

   private fun areNumeric(lhs: Value, rhs: Value) = lhs.type.isNumeric() && rhs.type.isNumeric()
   private fun areLists(lhs: Value, rhs: Value) = lhs.type == DataType.LIST && rhs.type == DataType.LIST
}