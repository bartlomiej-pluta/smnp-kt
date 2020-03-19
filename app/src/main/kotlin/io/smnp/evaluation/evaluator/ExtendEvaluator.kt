package io.smnp.evaluation.evaluator

import io.smnp.callable.method.CustomMethod
import io.smnp.callable.util.FunctionSignatureParser
import io.smnp.dsl.ast.model.node.ExtendNode
import io.smnp.dsl.ast.model.node.FunctionDefinitionNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.SingleTypeNode
import io.smnp.environment.Environment
import io.smnp.error.SmnpException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.util.ContextExceptionFactory.wrapWithContext

class ExtendEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(ExtendNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val (typeNode, methodsNode) = node as ExtendNode
      val type = FunctionSignatureParser.matcherForSingleTypeNode(typeNode as SingleTypeNode)

      methodsNode.children
         .map { it to CustomMethod.create(type, it as FunctionDefinitionNode) }
         .forEach {
            try {
               environment.defineMethod(it.second)
            } catch (e: SmnpException) {
               throw wrapWithContext(e, it.first.position, environment)
            }
         }

      return EvaluatorOutput.ok()
   }
}