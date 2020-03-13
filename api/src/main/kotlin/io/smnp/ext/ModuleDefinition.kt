package io.smnp.ext

import io.smnp.callable.function.Function
import io.smnp.callable.method.Method
import io.smnp.type.module.Module
import org.pf4j.ExtensionPoint

abstract class ModuleDefinition(val path: String) : ExtensionPoint {
    open fun functions(): List<Function> = emptyList()
    open fun methods(): List<Method> = emptyList()
    open fun dependencies(): List<String> = emptyList()

    fun module(): Module = Module.create(path, functions(), methods())
}