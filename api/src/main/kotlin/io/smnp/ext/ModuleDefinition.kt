package io.smnp.ext

import io.smnp.callable.function.Function
import io.smnp.callable.method.Method
import org.pf4j.ExtensionPoint

interface ModuleDefinition : ExtensionPoint {
    fun modulePath(): String
    fun functions(): List<Function>
    fun methods(): List<Method>
}