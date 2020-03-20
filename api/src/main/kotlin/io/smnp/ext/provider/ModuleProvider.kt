package io.smnp.ext.provider

import io.smnp.environment.Environment
import io.smnp.interpreter.LanguageModuleInterpreter
import io.smnp.type.module.Module
import org.pf4j.ExtensionPoint

abstract class ModuleProvider(val path: String) : ExtensionPoint {
    open fun onModuleLoad(environment: Environment) {}
    open fun beforeModuleDisposal(environment: Environment) {}
    open fun dependencies(): List<String> = emptyList()
    abstract fun provideModule(interpreter: LanguageModuleInterpreter): Module
}