package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.FunctionCallArgumentsNode
import io.smnp.dsl.ast.model.node.FunctionCallNode
import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.error.SmnpException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.util.ContextExceptionFactory.wrapWithContext

class FunctionCallEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(FunctionCallNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val evaluator = assert(ExpressionEvaluator(), "expression")
      val (identifierNode, argsNode) = node as FunctionCallNode
      val identifier = (identifierNode as IdentifierNode).token.rawValue
      val arguments = (argsNode as FunctionCallArgumentsNode).items.map { evaluator.evaluate(it, environment).value }

      try {
         return EvaluatorOutput.value(environment.invokeFunction(identifier, arguments))
      } catch (e: SmnpException) {
         throw wrapWithContext(e, identifierNode.position, environment)
      }
   }
}