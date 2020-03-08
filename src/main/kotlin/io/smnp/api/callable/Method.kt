package io.smnp.api.callable

import io.smnp.api.environment.Environment
import io.smnp.api.matcher.Matcher
import io.smnp.api.model.Value

abstract class Method(val typeMatcher: Matcher, val name: String) {
    private var definitions: List<MethodDefinition> = mutableListOf()

    abstract fun define(new: MethodDefinitionTool)

    init {
        definitions = MethodDefinitionTool().apply { define(this) }.definitions
    }

    fun verifyType(type: Value) = typeMatcher.match(type)

    fun call(environment: Environment, obj: Value, vararg arguments: Value): Value {
        val (definition, args) = definitions
            .map { Pair(it, it.signature.parse(arguments.toList())) }
            .first { (_, args) -> args.signatureMatched }
        // TODO: Throw exception if signature is not matched
        return definition.body(environment, obj, args.arguments)
    }

    val signature: String
        get() = definitions.joinToString("\nor\n") { "$typeMatcher.$name${it.signature}" }
}