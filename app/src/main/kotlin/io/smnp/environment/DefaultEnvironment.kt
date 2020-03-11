package io.smnp.environment

import io.smnp.callable.function.Function
import io.smnp.callable.method.Method
import io.smnp.callable.signature.ActualSignatureFormatter.format
import io.smnp.ext.DefaultModuleRegistry
import io.smnp.runtime.model.CallStack
import io.smnp.type.model.Value
import io.smnp.type.module.Module

class DefaultEnvironment : Environment {
    private val rootModule = Module("<root>")
    private val loadedModules = mutableListOf<String>()
    private val callStack = CallStack()

    init {
        callStack.push(rootModule, "<entrypoint>", emptyList())
    }

    override fun loadModule(path: String) {
        if(!loadedModules.contains(path)) {
            DefaultModuleRegistry.requestModulesForPath(path).forEach {
                rootModule.addSubmodule(it)
                loadedModules.add(path)
            }
        }
    }

    override fun printModules(printContent: Boolean) {
        rootModule.pretty(printContent)
    }

    override fun invokeFunction(name: String, arguments: List<Value>): Value {
        val foundFunctions = rootModule.findFunction(name)
        if(foundFunctions.isEmpty()) {
            throw RuntimeException("No function found with name of '$name'")
        }

        if(foundFunctions.size > 1) {
            throw RuntimeException("Found ${foundFunctions.size} functions with name of $foundFunctions")
        }

        val function = foundFunctions[0]

        callStack.push(function.module, function.name, arguments)
        val value = function.call(this, *arguments.toTypedArray())
        callStack.pop()

        return value
    }

    override fun invokeMethod(obj: Value, name: String, arguments: List<Value>): Value {
        val foundMethods = rootModule.findMethod(obj, name)
        if(foundMethods.isEmpty()) {
            throw RuntimeException("No method found with name of '$name' for ${format(obj)}")
        }

        if(foundMethods.size > 1) {
            throw RuntimeException("Found ${foundMethods.size} methods with name of '$foundMethods' for ${format(obj)}")
        }

        val method = foundMethods[0]

        callStack.push(method.module, "${method.typeMatcher}.${method.name}", arguments)
        val value = method.call(this, obj, *arguments.toTypedArray())
        callStack.pop()

        return value
    }

    override fun printCallStack() {
        callStack.pretty()
    }

    override fun defineFunction(function: Function) {
        rootModule.addFunction(function)
    }

    override fun defineMethod(method: Method) {
        rootModule.addMethod(method)
    }
}