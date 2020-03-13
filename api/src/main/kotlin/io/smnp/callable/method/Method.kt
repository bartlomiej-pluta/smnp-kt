package io.smnp.callable.method

import io.smnp.environment.Environment
import io.smnp.error.MethodInvocationException
import io.smnp.type.matcher.Matcher
import io.smnp.type.model.Value
import io.smnp.type.module.Module

abstract class Method(val typeMatcher: Matcher, val name: String) {
    private var definitions: List<MethodDefinition> = mutableListOf()
    private var _module: Module? = null
    var module: Module
        get() = _module ?: throw RuntimeException("Method has not set module yet")
        set(value) {
            _module = value
        }

    abstract fun define(new: MethodDefinitionTool)

    init {
        definitions = MethodDefinitionTool().apply { define(this) }.definitions
    }

    fun verifyType(type: Value) = typeMatcher.match(type)

    fun call(environment: Environment, obj: Value, vararg arguments: Value): Value {
        val (definition, args) = definitions
            .map { Pair(it, it.signature.parse(arguments.toList())) }
            .firstOrNull { (_, args) -> args.signatureMatched }
            ?: throw MethodInvocationException(this, obj, arguments, environment)

        return definition.body(environment, obj, args.arguments)
    }

    val signature: String
        get() = definitions.joinToString("\nor\n") { "$typeMatcher.$name${it.signature}" }
}