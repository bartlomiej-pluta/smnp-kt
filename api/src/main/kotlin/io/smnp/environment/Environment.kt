package io.smnp.environment

import io.smnp.callable.function.Function
import io.smnp.callable.method.Method
import io.smnp.type.model.Value

interface Environment {
    fun loadModule(path: String)
    fun printModules(printContent: Boolean)
    fun invokeFunction(name: String, arguments: List<Value>): Value
    fun invokeMethod(obj: Value, name: String, arguments: List<Value>): Value
    fun printCallStack()
    fun defineFunction(function: Function)
    fun defineMethod(method: Method)
    fun pushScope(scope: MutableMap<String, Value> = mutableMapOf())
    fun popScope(): Map<String, Value>?
    fun printScopes()
    fun setVariable(name: String, value: Value)
    fun getVariable(name: String): Value
}