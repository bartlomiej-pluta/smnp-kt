package com.bartlomiejpluta.smnp.callable.util

import com.bartlomiejpluta.smnp.dsl.ast.model.node.FunctionDefinitionArgumentsNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.IdentifierNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.OptionalFunctionDefinitionArgumentNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.RegularFunctionDefinitionArgumentNode
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.ShouldNeverReachThisLineException
import com.bartlomiejpluta.smnp.evaluation.evaluator.ExpressionEvaluator
import com.bartlomiejpluta.smnp.type.model.Value

object FunctionEnvironmentProvider {
    private val evaluator = ExpressionEvaluator()

    fun provideEnvironment(
        signature: FunctionDefinitionArgumentsNode,
        actualArgs: List<Value>,
        environment: Environment
    ): Map<String, Value> {
        return signature.items.mapIndexed { index, node ->
            when (node) {
                is RegularFunctionDefinitionArgumentNode -> regularArgument(node, actualArgs, index)
                is OptionalFunctionDefinitionArgumentNode -> optionalArgument(node, environment, actualArgs, index)
                else -> throw ShouldNeverReachThisLineException()
            }
        }.toMap()
    }

    private fun regularArgument(
        node: RegularFunctionDefinitionArgumentNode,
        actualArgs: List<Value>,
        index: Int
    ) = (node.identifier as IdentifierNode).token.rawValue to actualArgs[index]

    private fun optionalArgument(
        node: OptionalFunctionDefinitionArgumentNode,
        environment: Environment,
        actualArgs: List<Value>,
        index: Int
    ) = (node.identifier as IdentifierNode).token.rawValue to
            if (index < actualArgs.size) actualArgs[index]
            else evaluator.evaluate(node.defaultValue, environment).value

}