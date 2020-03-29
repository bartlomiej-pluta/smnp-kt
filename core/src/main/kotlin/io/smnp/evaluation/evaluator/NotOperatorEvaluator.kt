package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.NotOperatorNode
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import io.smnp.type.enumeration.DataType
import io.smnp.type.model.Value

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