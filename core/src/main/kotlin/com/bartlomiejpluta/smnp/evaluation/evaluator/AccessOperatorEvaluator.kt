package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.*
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.SmnpException
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException

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