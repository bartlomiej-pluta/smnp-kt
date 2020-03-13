package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.LogicOperatorNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.TokenNode
import io.smnp.dsl.token.model.enumeration.TokenType
import io.smnp.environment.Environment
import io.smnp.error.EnvironmentException
import io.smnp.error.EvaluationException
import io.smnp.error.PositionException
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.type.enumeration.DataType
import io.smnp.type.model.Value

class LogicOperatorEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(LogicOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val evaluator = ExpressionEvaluator()
      val (lhsNode, opNode, rhsNode) = (node as LogicOperatorNode)
      val lhs = evaluator.evaluate(lhsNode, environment).value!!
      val rhs = evaluator.evaluate(rhsNode, environment).value!!
      val operator = (opNode as TokenNode).token.type

      if (lhs.type != DataType.BOOL) {
         throw PositionException(
            EnvironmentException(
               EvaluationException("Operator '${operator.token}' supports only bool types"),
               environment
            ),
            lhsNode.position
         )
      }

      if (rhs.type != DataType.BOOL) {
         throw PositionException(
            EnvironmentException(
               EvaluationException("Operator '${operator.token}' supports only bool types"),
               environment
            ),
            rhsNode.position
         )
      }

      return EvaluatorOutput.value(
         when (operator) {
            TokenType.AND -> Value.bool((lhs.value as Boolean) && (rhs.value as Boolean))
            TokenType.OR -> Value.bool((lhs.value as Boolean) || (rhs.value as Boolean))
            else -> throw ShouldNeverReachThisLineException()
         }
      )
   }
}