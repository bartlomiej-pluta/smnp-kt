package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.ThrowNode
import io.smnp.environment.Environment
import io.smnp.error.CustomException
import io.smnp.error.EvaluationException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.util.ContextExceptionFactory
import io.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import io.smnp.evaluation.util.ContextExceptionFactory.wrapWithContext
import io.smnp.type.enumeration.DataType

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