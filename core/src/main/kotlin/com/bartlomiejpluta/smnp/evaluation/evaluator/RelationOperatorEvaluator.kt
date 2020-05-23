package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.RelationOperatorNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.TokenNode
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.ShouldNeverReachThisLineException
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.evaluation.util.NumberUnification.unify
import com.bartlomiejpluta.smnp.type.model.Value

class RelationOperatorEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(RelationOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val evaluator = ExpressionEvaluator()
      val (lhsNode, opNode, rhsNode) = (node as RelationOperatorNode)
      val lhs = evaluator.evaluate(lhsNode, environment).value
      val rhs = evaluator.evaluate(rhsNode, environment).value
      val operator = (opNode as TokenNode).token.rawValue

      if (operator in listOf("==", "!=")) {
         return EvaluatorOutput.value(
            Value.bool(
               if (operator == "==") lhs.value == rhs.value
               else lhs.value != rhs.value
            )
         )
      }

      if (!lhs.type.isNumeric() || !rhs.type.isNumeric()) {
         throw contextEvaluationException("Operator $operator supports only numeric types", node.position, environment)
      }

      return EvaluatorOutput.value(
         when (operator) {
            ">" -> unify(lhs, rhs, int = { (l, r) -> Value.bool(l > r) }, float = { (l, r) -> Value.bool(l > r) })
            "<" -> unify(lhs, rhs, int = { (l, r) -> Value.bool(l < r) }, float = { (l, r) -> Value.bool(l < r) })
            ">=" -> unify(lhs, rhs, int = { (l, r) -> Value.bool(l >= r) }, float = { (l, r) -> Value.bool(l >= r) })
            "<=" -> unify(lhs, rhs, int = { (l, r) -> Value.bool(l <= r) }, float = { (l, r) -> Value.bool(l <= r) })
            else -> throw ShouldNeverReachThisLineException()
         }
      )
   }
}