package io.smnp.callable.util

import io.smnp.dsl.ast.model.node.FunctionDefinitionArgumentsNode
import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.OptionalFunctionDefinitionArgumentNode
import io.smnp.dsl.ast.model.node.RegularFunctionDefinitionArgumentNode
import io.smnp.environment.Environment
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.evaluation.evaluator.ExpressionEvaluator
import io.smnp.type.model.Value

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
            else evaluator.evaluate(node.defaultValue, environment).value!!

}