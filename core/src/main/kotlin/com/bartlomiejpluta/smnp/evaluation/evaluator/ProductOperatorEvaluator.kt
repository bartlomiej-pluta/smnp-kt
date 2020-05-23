package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.ProductOperatorNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.TokenNode
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.ShouldNeverReachThisLineException
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.evaluation.util.NumberUnification.unify
import com.bartlomiejpluta.smnp.type.model.Value

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