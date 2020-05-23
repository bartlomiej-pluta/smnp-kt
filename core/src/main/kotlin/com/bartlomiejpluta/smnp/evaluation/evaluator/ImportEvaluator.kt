package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.IdentifierNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.ImportNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.SmnpException
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.wrapWithContext

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