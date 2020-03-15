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
   private val evaluator = ExpressionEvaluator()
   override fun supportedNodes() = listOf(LogicOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val (lhsNode, opNode, rhsNode) = (node as LogicOperatorNode)
      val operator = (opNode as TokenNode).token.type

      return EvaluatorOutput.value(
         Value.bool(
            when (operator) {
               TokenType.AND -> if(evalOperand(lhsNode, operator, environment)) evalOperand(rhsNode, operator, environment) else false
               TokenType.OR -> if (evalOperand(lhsNode, operator, environment)) true else evalOperand(rhsNode, operator, environment)
               else -> throw ShouldNeverReachThisLineException()
            }
         )
      )
   }

   private fun evalOperand(operand: Node, operator: TokenType, environment: Environment): Boolean {
      val value = evaluator.evaluate(operand, environment).value

      if (value.type != DataType.BOOL) {
         throw PositionException(
            EnvironmentException(
               EvaluationException("Operator '${operator.token}' supports only bool types, found ${value.typeName}"),
               environment
            ),
            operand.position
         )
      }

      return value.value as Boolean
   }
}