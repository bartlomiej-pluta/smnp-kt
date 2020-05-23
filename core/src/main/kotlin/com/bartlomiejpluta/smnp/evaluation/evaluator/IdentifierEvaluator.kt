package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.IdentifierNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.EvaluationException
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.wrapWithContext

class IdentifierEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(IdentifierNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val identifier = (node as IdentifierNode).token.rawValue

      try {
         return EvaluatorOutput.value(environment.getVariable(identifier))
      } catch (e: EvaluationException) {
         throw wrapWithContext(e, node.position, environment)
      }
   }
}