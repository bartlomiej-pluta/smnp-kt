package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.ConditionNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.NoneNode
import io.smnp.environment.Environment
import io.smnp.error.EnvironmentException
import io.smnp.error.EvaluationException
import io.smnp.error.PositionException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.type.enumeration.DataType

class ConditionEvaluator : Evaluator() {
   private val expressionEvaluator = ExpressionEvaluator()
   private val defaultEvaluator = DefaultEvaluator()

   override fun supportedNodes() = listOf(ConditionNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val (conditionNode, trueBranchNode, falseBranchNode) = (node as ConditionNode)
      val condition = expressionEvaluator.evaluate(conditionNode, environment).value!!

      if (condition.type != DataType.BOOL) {
         throw PositionException(
            EnvironmentException(
               EvaluationException("Condition should be of bool type, found '${condition.value}'"),
               environment
            ),
            conditionNode.position
         )
      }

      if (condition.value!! as Boolean) {
         return defaultEvaluator.evaluate(trueBranchNode, environment)
      } else if (falseBranchNode !is NoneNode) {
         return defaultEvaluator.evaluate(falseBranchNode, environment)
      }

      return EvaluatorOutput.ok()
   }
}