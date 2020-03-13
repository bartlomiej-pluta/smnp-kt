package io.smnp.evaluation.evaluator

import io.smnp.callable.method.CustomMethod
import io.smnp.callable.util.FunctionSignatureParser
import io.smnp.dsl.ast.model.node.*
import io.smnp.environment.Environment
import io.smnp.error.EnvironmentException
import io.smnp.error.PositionException
import io.smnp.error.SmnpException
import io.smnp.evaluation.model.entity.EvaluatorOutput

class ExtendEvaluator : Evaluator() {
   override fun supportedNodes() = listOf(ExtendNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val (typeNode, identifierNode, methodsNode) = node as ExtendNode
      val type = FunctionSignatureParser.matcherForSingleTypeNode(typeNode as SingleTypeNode)
      val identifier = (identifierNode as IdentifierNode).token.rawValue

      methodsNode.children
         .map { it to CustomMethod.create(type, identifier, it as FunctionDefinitionNode) }
         .forEach {
            try {
               environment.defineMethod(it.second)
            } catch (e: SmnpException) {
               throw PositionException(EnvironmentException(e, environment), it.first.position)
            }
         }

      return EvaluatorOutput.ok()
   }
}