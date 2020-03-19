package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.*
import io.smnp.environment.Environment
import io.smnp.error.SmnpException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.util.ContextExceptionFactory
import io.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException

class AccessOperatorEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(AccessOperatorNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val evaluator = ExpressionEvaluator()
      val (lhsNode, _, rhsNode) = (node as AccessOperatorNode)
      val lhs = evaluator.evaluate(lhsNode, environment).value

      return when (rhsNode) {
         is IdentifierNode -> {
            val rhs = rhsNode.token.rawValue
            EvaluatorOutput.value(
               lhs.properties[rhs] ?: throw contextEvaluationException(
                  "Unknown property $rhs of type ${lhs.typeName}",
                  rhsNode.position,
                  environment
               )
            )
         }
         is FunctionCallNode -> {
            val (identifierNode, argsNode) = rhsNode
            val identifier = (identifierNode as IdentifierNode).token.rawValue
            val arguments =
               (argsNode as FunctionCallArgumentsNode).items.map { evaluator.evaluate(it, environment).value }
            try {
               return EvaluatorOutput.value(environment.invokeMethod(lhs, identifier, arguments))
            } catch (e: SmnpException) {
               throw ContextExceptionFactory.wrapWithContext(e, identifierNode.position, environment)
            }
         }
         else -> {
            throw contextEvaluationException(
               "Invalid property access type - only property name and method call are allowed",
               rhsNode.position,
               environment
            )
         }
      }
   }
}