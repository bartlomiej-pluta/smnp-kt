package com.bartlomiejpluta.smnp.ext.provider

import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.interpreter.LanguageModuleInterpreter
import com.bartlomiejpluta.smnp.type.module.Module
import org.pf4j.ExtensionPoint

abstract class ModuleProvider(val path: String) : ExtensionPoint {
    open fun onModuleLoad(environment: Environment) {}
    open fun beforeModuleDisposal(environment: Environment) {}
    open fun dependencies(): List<String> = emptyList()
    abstract fun provideModule(interpreter: LanguageModuleInterpreter): Module
}