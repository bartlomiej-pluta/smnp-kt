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
    private val scopesStack = Stack.of<MutableMap<String, Value>>(mutableMapOf())

    fun pushScope(scope: MutableMap<String, Value> = mutableMapOf()) {
        scopesStack.push(scope)
    }

    fun popScope() = scopesStack.pop()

    val scopesCount: Int
    get() = scopesStack.size

    fun setVariable(name: String, value: Value) {
        val scope = scopesStack.lastOrNull { it.containsKey(name) } ?: scopesStack.top()
        scope[name] = value
    }

    fun getVariable(name: String): Value {
        return scopesStack.lastOrNull { it.containsKey(name) }?.get(name) ?: throw EvaluationException("Undefined variable `$name`")
    }

    fun prettyScope() {
        scopes.forEach { println(it) }
    }

    val scopes: List<String>
    get() = scopesStack.asReversed().mapIndexed { index, item -> "[${scopesStack.size - index - 1}] $item" }

    override fun toString() = "${module.canonicalName}::$name${ActualSignatureFormatter.format(arguments.toTypedArray())}"
}