package io.smnp.callable.function

import io.smnp.environment.Environment
import io.smnp.error.FunctionInvocationException
import io.smnp.type.model.Value

abstract class Function(val name: String) {
    private var definitions: List<FunctionDefinition> = mutableListOf()

    abstract fun define(new: FunctionDefinitionTool)

    init {
        definitions = FunctionDefinitionTool().apply { define(this) }.definitions
    }

    fun call(environment: Environment, vararg arguments: Value): Value {
        val (definition, args) = definitions
            .map { Pair(it, it.signature.parse(arguments.toList())) }
            .firstOrNull { (_, args) -> args.signatureMatched }
            ?: throw FunctionInvocationException(this, arguments)

        return definition.body(environment, args.arguments)
    }

    val signature: String
        get() = definitions.joinToString("\nor\n") { "$name${it.signature}" }
}