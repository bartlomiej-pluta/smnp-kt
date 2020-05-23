package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.LogicOperatorNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.TokenNode
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.ShouldNeverReachThisLineException
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.type.enumeration.DataType
import com.bartlomiejpluta.smnp.type.model.Value

class LogicOperatorEvaluator : Evaluator() {
   private val evaluator = ExpressionEvaluator()
   override fun supportedNodes() = listOf(LogicOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val (lhsNode, opNode, rhsNode) = (node as LogicOperatorNode)
      val operator = (opNode as TokenNode).token.type

      return EvaluatorOutput.value(
         Value.bool(
            when (operator) {
               TokenType.AND -> if (evalOperand(lhsNode, operator, environment)) evalOperand(
                  rhsNode,
                  operator,
                  environment
               ) else false
               TokenType.OR -> if (evalOperand(lhsNode, operator, environment)) true else evalOperand(
                  rhsNode,
                  operator,
                  environment
               )
               else -> throw ShouldNeverReachThisLineException()
            }
         )
      )
   }

   private fun evalOperand(operand: Node, operator: TokenType, environment: Environment): Boolean {
      val value = evaluator.evaluate(operand, environment).value

      if (value.type != DataType.BOOL) {
         throw contextEvaluationException(
            "Operator '${operator.token}' supports only bool types, found ${value.typeName}",
            operand.position,
            environment
         )
      }

      return value.value as Boolean
   }
}