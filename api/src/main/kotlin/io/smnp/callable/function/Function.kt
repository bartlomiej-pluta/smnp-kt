package io.smnp.callable.function

import io.smnp.environment.Environment
import io.smnp.type.model.Value
import io.smnp.type.module.Module

abstract class Function(val name: String) {
    private var definitions: List<FunctionDefinition> = mutableListOf()
    private var _module: Module? = null
    var module: Module
        get() = _module ?: throw RuntimeException("Function has not set module yet")
        set(value) {
            _module = value
        }

    abstract fun define(new: FunctionDefinitionTool)

    init {
        definitions = FunctionDefinitionTool().apply { define(this) }.definitions
    }

    fun matches(arguments: List<Value>) = matchArguments(arguments) != null

    private fun matchArguments(arguments: List<Value>) = definitions
        .map { it to it.signature.parse(arguments.toList()) }
        .firstOrNull { (_, args) -> args.signatureMatched }

    fun call(environment: Environment, vararg arguments: Value): Value {
        val (definition, args) = matchArguments(arguments.toList())!! // todo?

        return definition.body(environment, args.arguments)
    }

    val signature = definitions.map { "$name${it.signature}" }
}