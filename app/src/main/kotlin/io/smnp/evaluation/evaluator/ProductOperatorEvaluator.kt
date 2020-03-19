package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.ProductOperatorNode
import io.smnp.dsl.ast.model.node.TokenNode
import io.smnp.dsl.token.model.enumeration.TokenType
import io.smnp.environment.Environment
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import io.smnp.evaluation.util.NumberUnification.unify
import io.smnp.type.model.Value

class ProductOperatorEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(ProductOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val evaluator = ExpressionEvaluator()
      val (lhsNode, opNode, rhsNode) = (node as ProductOperatorNode)
      val lhs = evaluator.evaluate(lhsNode, environment).value
      val rhs = evaluator.evaluate(rhsNode, environment).value
      val operator = (opNode as TokenNode).token.type

      if (!lhs.type.isNumeric() || !rhs.type.isNumeric()) {
         throw contextEvaluationException(
            "Operator ${operator.token} supports only numeric types",
            node.position,
            environment
         )
      }

      return EvaluatorOutput.value(
         when (operator) {
            TokenType.ASTERISK -> unify(
               lhs,
               rhs,
               int = { (l, r) -> Value.int(l * r) },
               float = { (l, r) -> Value.float(l * r) })
            TokenType.SLASH -> unify(
               lhs,
               rhs,
               int = { (l, r) -> Value.int(l / r) },
               float = { (l, r) -> Value.float(l / r) })
            else -> throw ShouldNeverReachThisLineException()
         }
      )
   }
}