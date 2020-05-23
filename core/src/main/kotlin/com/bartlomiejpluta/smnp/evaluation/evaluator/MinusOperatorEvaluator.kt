package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.MinusOperatorNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.type.enumeration.DataType
import com.bartlomiejpluta.smnp.type.model.Value

class MinusOperatorEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(MinusOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val evaluator = ExpressionEvaluator()
      val (_, operandNode) = (node as MinusOperatorNode)
      val operand = evaluator.evaluate(operandNode, environment)

      return EvaluatorOutput.value(
         when (operand.value.type) {
            DataType.INT -> Value.int(-1 * operand.value.value as Int)
            DataType.FLOAT -> Value.float(-1.0f * operand.value.value as Float)
            DataType.STRING -> Value.string((operand.value.value as String).reversed())
            DataType.LIST -> Value.list((operand.value.value as List<Value>).reversed())
            else -> throw contextEvaluationException(
               "Type ${operand.value.typeName} does not support minus operator",
               node.position,
               environment
            )
         }
      )
   }
}