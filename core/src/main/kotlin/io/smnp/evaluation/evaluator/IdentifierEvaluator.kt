package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.error.EvaluationException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.util.ContextExceptionFactory.wrapWithContext

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