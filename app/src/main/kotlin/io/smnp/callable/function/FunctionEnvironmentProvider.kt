package io.smnp.callable.function

import io.smnp.dsl.ast.model.node.FunctionDefinitionArgumentsNode
import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.ast.model.node.OptionalFunctionDefinitionArgumentNode
import io.smnp.dsl.ast.model.node.RegularFunctionDefinitionArgumentNode
import io.smnp.environment.Environment
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.evaluation.evaluator.ExpressionEvaluator
import io.smnp.type.model.Value

object FunctionEnvironmentProvider {
    fun provideEnvironment(signature: FunctionDefinitionArgumentsNode, actualArgs: List<Value>, environment: Environment): Map<String, Value> {
        val evaluator = ExpressionEvaluator()
        return signature.items.mapIndexed { index, node ->
            when (node) {
                is RegularFunctionDefinitionArgumentNode -> (node.identifier as IdentifierNode).token.rawValue to actualArgs[index]
                is OptionalFunctionDefinitionArgumentNode -> (node.identifier as IdentifierNode).token.rawValue to evaluator.evaluate(
                    node.defaultValue,
                    environment
                ).value!!
                else -> throw ShouldNeverReachThisLineException()
            }
        }.toMap()
    }
}