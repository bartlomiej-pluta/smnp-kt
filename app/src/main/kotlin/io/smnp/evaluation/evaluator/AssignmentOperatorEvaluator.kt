package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.AssignmentOperatorNode
import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.error.EnvironmentException
import io.smnp.error.EvaluationException
import io.smnp.error.PositionException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.type.enumeration.DataType

class AssignmentOperatorEvaluator : Evaluator() {
   private val evaluator = assert(ExpressionEvaluator(), "expression")

   override fun supportedNodes() = listOf(AssignmentOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val (identifierNode, _, valueNode) = node as AssignmentOperatorNode
      val identifier = (identifierNode as IdentifierNode).token.rawValue
      val value = evaluator.evaluate(valueNode, environment).value

      if (value.type == DataType.VOID) {
         throw PositionException(
            EnvironmentException(
               EvaluationException("Right hand side expression of assignment operation has returned nothing"),
               environment
            ),
            valueNode.position
         )
      }

      environment.setVariable(identifier, value)

      return EvaluatorOutput.value(value)
   }
}