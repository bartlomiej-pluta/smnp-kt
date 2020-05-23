package com.bartlomiejpluta.smnp.callable.method

import com.bartlomiejpluta.smnp.callable.util.FunctionEnvironmentProvider
import com.bartlomiejpluta.smnp.callable.util.FunctionSignatureParser
import com.bartlomiejpluta.smnp.dsl.ast.model.node.FunctionDefinitionArgumentsNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.FunctionDefinitionNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.IdentifierNode
import com.bartlomiejpluta.smnp.evaluation.evaluator.BlockEvaluator
import com.bartlomiejpluta.smnp.evaluation.model.exception.Return
import com.bartlomiejpluta.smnp.type.matcher.Matcher
import com.bartlomiejpluta.smnp.type.model.Value

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