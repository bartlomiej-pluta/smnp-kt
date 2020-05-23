package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.FunctionCallArgumentsNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.FunctionCallNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.IdentifierNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.SmnpException
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.wrapWithContext

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