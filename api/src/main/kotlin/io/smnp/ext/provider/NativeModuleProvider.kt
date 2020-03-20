package io.smnp.ext.provider

import io.smnp.callable.function.Function
import io.smnp.callable.method.Method
import io.smnp.ext.provider.ModuleProvider
import io.smnp.interpreter.LanguageModuleInterpreter
import io.smnp.type.module.Module

abstract class NativeModuleProvider(path: String) : ModuleProvider(path) {
   open fun functions(): List<Function> = emptyList()
   open fun methods(): List<Method> = emptyList()

   final override fun provideModule(interpreter: LanguageModuleInterpreter) = Module.create(path, functions(), methods())
}