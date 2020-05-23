package com.bartlomiejpluta.smnp.environment

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.method.Method
import com.bartlomiejpluta.smnp.type.model.Value
import com.bartlomiejpluta.smnp.type.module.Module

interface Environment {
   fun loadModule(path: String)
   fun printModules(printContent: Boolean)
   fun invokeFunction(name: String, arguments: List<Value>): Value
   fun invokeMethod(obj: Value, name: String, arguments: List<Value>): Value
   fun printCallStack(scopes: Boolean = false)
   fun stackTrace(): String
   fun defineFunction(function: Function)
   fun defineMethod(method: Method)
   fun pushScope(scope: MutableMap<String, Value> = mutableMapOf())
   fun popScope(): Map<String, Value>?
   fun printScopes()
   fun setVariable(name: String, value: Value)
   fun getVariable(name: String): Value
   fun dispose()
   fun getRootModule(): Module
}