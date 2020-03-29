package io.smnp.callable.method

import io.smnp.callable.util.FunctionEnvironmentProvider
import io.smnp.callable.util.FunctionSignatureParser
import io.smnp.dsl.ast.model.node.FunctionDefinitionArgumentsNode
import io.smnp.dsl.ast.model.node.FunctionDefinitionNode
import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.evaluation.evaluator.BlockEvaluator
import io.smnp.evaluation.model.exception.Return
import io.smnp.type.matcher.Matcher
import io.smnp.type.model.Value

object CustomMethod {
    fun create(type: Matcher, node: FunctionDefinitionNode): Method {
        val identifier = (node.identifier as IdentifierNode).token.rawValue

        return object : Method(type, identifier) {
            override fun define(new: MethodDefinitionTool) {
                val (_, argumentsNode, bodyNode) = node
                val signature = FunctionSignatureParser.parseSignature(argumentsNode as FunctionDefinitionArgumentsNode)
                val evaluator = BlockEvaluator(dedicatedScope = false)

                new method signature body { env, obj, args ->
                    val boundArguments =
                        FunctionEnvironmentProvider.provideEnvironment(argumentsNode, args, env).toMutableMap()
                    boundArguments["this"] = obj

                    try {
                        env.pushScope(boundArguments)
                        evaluator.evaluate(bodyNode, env)
                    } catch (value: Return) {
                        return@body value.value
                    } finally {
                        env.popScope()
                    }

                    Value.void()
                }
            }
        }
    }
}