package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.callable.function.CustomFunction
import com.bartlomiejpluta.smnp.dsl.ast.model.node.FunctionDefinitionNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.SmnpException
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.wrapWithContext

class FunctionDefinitionEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(FunctionDefinitionNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val function = CustomFunction.create(node as FunctionDefinitionNode)

      try {
         environment.defineFunction(function)
      } catch (e: SmnpException) {
         throw wrapWithContext(e, node.position, environment)
      }

      return EvaluatorOutput.ok()
   }
}