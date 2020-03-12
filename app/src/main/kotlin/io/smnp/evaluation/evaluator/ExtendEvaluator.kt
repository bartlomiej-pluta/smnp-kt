package io.smnp.evaluation.evaluator

import io.smnp.callable.method.CustomMethod
import io.smnp.callable.util.FunctionSignatureParser
import io.smnp.dsl.ast.model.node.*
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class ExtendEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(ExtendNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val (typeNode, identifierNode, methodsNode) = node as ExtendNode
        val type = FunctionSignatureParser.matcherForSingleTypeNode(typeNode as SingleTypeNode)
        val identifier = (identifierNode as IdentifierNode).token.rawValue

        methodsNode.children
            .map { CustomMethod.create(type, identifier, it as FunctionDefinitionNode) }
            .forEach { environment.defineMethod(it) }

        return EvaluatorOutput.ok()
    }
}