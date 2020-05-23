package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.PowerOperatorNode
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.type.model.Value
import kotlin.math.pow

class PowerOperatorEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(PowerOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val (lhsNode, _, rhsNode) = (node as PowerOperatorNode)
      val evaluator = ExpressionEvaluator()
      val lhs = evaluator.evaluate(lhsNode, environment).value
      val rhs = evaluator.evaluate(rhsNode, environment).value

      if (!lhs.type.isNumeric() || !rhs.type.isNumeric()) {
         throw contextEvaluationException("Operator ** supports only numeric types", node.position, environment)
      }

      return EvaluatorOutput.value(Value.float((lhs.value as Number).toFloat().pow((rhs.value as Number).toFloat())))
   }
}