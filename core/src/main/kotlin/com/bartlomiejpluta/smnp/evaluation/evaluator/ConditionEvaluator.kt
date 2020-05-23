package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.ConditionNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.NoneNode
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.type.enumeration.DataType

class ConditionEvaluator : Evaluator() {
   private val expressionEvaluator = ExpressionEvaluator()
   private val defaultEvaluator = DefaultEvaluator()

   override fun supportedNodes() = listOf(ConditionNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val (conditionNode, trueBranchNode, falseBranchNode) = (node as ConditionNode)
      val condition = expressionEvaluator.evaluate(conditionNode, environment).value

      if (condition.type != DataType.BOOL) {
         throw contextEvaluationException(
            "Condition should be of bool type, found '${condition.value}'",
            conditionNode.position,
            environment
         )
      }

      if (condition.value as Boolean) {
         return defaultEvaluator.evaluate(trueBranchNode, environment)
      } else if (falseBranchNode !is NoneNode) {
         return defaultEvaluator.evaluate(falseBranchNode, environment)
      }

      return EvaluatorOutput.ok()
   }
}