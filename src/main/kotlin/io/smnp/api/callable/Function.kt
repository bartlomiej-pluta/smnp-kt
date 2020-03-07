package io.smnp.api.callable

import io.smnp.api.environment.Environment
import io.smnp.api.model.Value

abstract class Function(val name: String) {
    private var definitions: List<FunctionDefinition> = mutableListOf()

    abstract fun define(new: FunctionDefinitionTool)

    init {
        definitions = FunctionDefinitionTool().apply { define(this) }.definitions
    }

    fun call(environment: Environment, vararg arguments: Value): Value {
        val (definition, args) = definitions
            .map { Pair(it, it.signature.parse(arguments.toList())) }
            .first { (_, args) -> args.signatureMatched }
            // TODO: Throw exception if signature is not matched
        return definition.body(environment, args.arguments)
    }

    val signature: String
        get() = definitions.joinToString("\nor\n") { "$name${it.signature}" }
}