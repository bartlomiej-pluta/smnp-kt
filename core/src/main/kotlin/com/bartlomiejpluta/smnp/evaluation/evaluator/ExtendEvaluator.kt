package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.callable.method.CustomMethod
import com.bartlomiejpluta.smnp.callable.util.FunctionSignatureParser
import com.bartlomiejpluta.smnp.dsl.ast.model.node.ExtendNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.FunctionDefinitionNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.SingleTypeNode
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.SmnpException
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.wrapWithContext

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