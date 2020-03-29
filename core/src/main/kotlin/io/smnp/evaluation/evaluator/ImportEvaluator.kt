package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.ImportNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.error.SmnpException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.util.ContextExceptionFactory.wrapWithContext

class ImportEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(ImportNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val path = (node as ImportNode).path.joinToString(".") { (it as IdentifierNode).token.rawValue }

      try {
         environment.loadModule(path)
      } catch (e: SmnpException) {
         throw wrapWithContext(e, node.position, environment)
      }

      return EvaluatorOutput.ok()
   }
}