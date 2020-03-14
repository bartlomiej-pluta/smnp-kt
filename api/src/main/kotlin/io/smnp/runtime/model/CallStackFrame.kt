package io.smnp.runtime.model

import io.smnp.callable.signature.ActualSignatureFormatter
import io.smnp.collection.Stack
import io.smnp.error.EvaluationException
import io.smnp.type.model.Value
import io.smnp.type.module.Module

data class CallStackFrame(
    val module: Module,
    val name: String,
    val arguments: List<Value>
) {
    private val scopes = Stack.of<MutableMap<String, Value>>(mutableMapOf())

    fun pushScope(scope: MutableMap<String, Value> = mutableMapOf()) {
        scopes.push(scope)
    }

    fun popScope() = scopes.pop()

    val scopesCount: Int
    get() = scopes.size

    fun setVariable(name: String, value: Value) {
        val scope = scopes.lastOrNull { it.containsKey(name) } ?: scopes.top()
        scope[name] = value
    }

    fun getVariable(name: String): Value {
        return scopes.lastOrNull { it.containsKey(name) }?.get(name) ?: throw EvaluationException("Undefined variable `$name`")
    }

    fun prettyScope() {
        scopes.asReversed().forEachIndexed { index, item -> println("[${scopes.size - index - 1}] $item") }
    }

    override fun toString() = "${module.canonicalName}::$name${ActualSignatureFormatter.format(arguments.toTypedArray())}"
}