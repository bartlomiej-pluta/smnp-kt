package io.smnp.ext

import io.smnp.interpreter.Interpreter
import io.smnp.type.module.Module
import org.pf4j.ExtensionPoint

abstract class ModuleProvider(val path: String) : ExtensionPoint {
    open fun dependencies(): List<String> = emptyList()
    abstract fun provideModule(interpreter: Interpreter): Module
}