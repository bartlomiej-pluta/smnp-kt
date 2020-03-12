package io.smnp.callable.function

import io.smnp.environment.Environment
import io.smnp.error.FunctionInvocationException
import io.smnp.error.RuntimeException
import io.smnp.type.model.Value
import io.smnp.type.module.Module

abstract class Function(val name: String) {
    private var definitions: List<FunctionDefinition> = mutableListOf()
    private var _module: Module? = null
    var module: Module
        get() = _module ?: throw RuntimeException("Function has not set module yet")
        set(value) {
            if (_module != null) {
                throw RuntimeException("Function of method is already set to ${module.canonicalName}")
            }
            _module = value
        }

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