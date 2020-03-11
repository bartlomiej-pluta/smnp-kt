package io.smnp.callable.function

import io.smnp.dsl.ast.model.node.FunctionDefinitionArgumentsNode
import io.smnp.dsl.ast.model.node.FunctionDefinitionNode
import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.type.model.Value

object CustomFunction {
    fun create(node: FunctionDefinitionNode): Function {
        val identifier = (node.identifier as IdentifierNode).token.rawValue

        return object : Function(identifier) {
            override fun define(new: FunctionDefinitionTool) {
                val (_, argumentsNode, bodyNode) = node
                val signature = FunctionSignatureParser.parseSignature(argumentsNode as FunctionDefinitionArgumentsNode)

                new function signature define { env, args ->
                    val boundArguments = FunctionEnvironmentProvider.provideEnvironment(argumentsNode, args, env)
                    // TODO(Implement bodyNode evaluation)
                    Value.void()
                }
            }
        }
    }
}