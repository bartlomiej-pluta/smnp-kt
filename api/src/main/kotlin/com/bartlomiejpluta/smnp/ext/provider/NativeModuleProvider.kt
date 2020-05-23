package com.bartlomiejpluta.smnp.ext.provider

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.method.Method
import com.bartlomiejpluta.smnp.interpreter.LanguageModuleInterpreter
import com.bartlomiejpluta.smnp.type.module.Module

abstract class NativeModuleProvider(path: String) : ModuleProvider(path) {
   open fun functions(): List<Function> = emptyList()
   open fun methods(): List<Method> = emptyList()

   final override fun provideModule(interpreter: LanguageModuleInterpreter) = Module.create(path, functions(), methods())
}