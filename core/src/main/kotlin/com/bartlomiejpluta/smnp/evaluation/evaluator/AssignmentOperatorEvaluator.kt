package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.AssignmentOperatorNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.IdentifierNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.type.enumeration.DataType

class AssignmentOperatorEvaluator : Evaluator() {
   private val evaluator = assert(ExpressionEvaluator(), "expression")

   override fun supportedNodes() = listOf(AssignmentOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val (identifierNode, _, valueNode) = node as AssignmentOperatorNode
      val identifier = (identifierNode as IdentifierNode).token.rawValue
      val value = evaluator.evaluate(valueNode, environment).value

      if (value.type == DataType.VOID) {
         throw contextEvaluationException(
            "Right hand side expression of assignment operation has returned nothing",
            valueNode.position,
            environment
         )
      }

      environment.setVariable(identifier, value)

      return EvaluatorOutput.value(value)
   }
}