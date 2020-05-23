package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.NotOperatorNode
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.type.enumeration.DataType
import com.bartlomiejpluta.smnp.type.model.Value

class NotOperatorEvaluator : Evaluator() {
   val evaluator = assert(ExpressionEvaluator(), "expression")
   override fun supportedNodes() = listOf(NotOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val (_, operandNode) = (node as NotOperatorNode)
      val operand = evaluator.evaluate(operandNode, environment).value

      if (operand.type != DataType.BOOL) {
         throw contextEvaluationException("Only bool types can be negated", operandNode.position, environment)
      }

      return EvaluatorOutput.value(Value.bool(!(operand.value as Boolean)))
   }
}