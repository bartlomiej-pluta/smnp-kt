package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.ThrowNode
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.CustomException
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.wrapWithContext
import com.bartlomiejpluta.smnp.type.enumeration.DataType

class ThrowEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(ThrowNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val evaluator = ExpressionEvaluator()
      val valueNode = (node as ThrowNode).value
      val value = evaluator.evaluate(valueNode, environment).value

      if (value.type != DataType.STRING) {
         throw contextEvaluationException(
            "Only string values can be thrown with 'throw' statement, found ${value.typeName}",
            valueNode.position,
            environment
         )
      }

      throw wrapWithContext(CustomException(value.value as String), node.position, environment)
   }
}