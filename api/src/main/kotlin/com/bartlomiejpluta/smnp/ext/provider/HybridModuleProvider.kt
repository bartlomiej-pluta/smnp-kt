package com.bartlomiejpluta.smnp.ext.provider

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.method.Method
import com.bartlomiejpluta.smnp.interpreter.LanguageModuleInterpreter
import com.bartlomiejpluta.smnp.type.module.Module

abstract class HybridModuleProvider(path: String) : ModuleProvider(path) {
   open fun functions(): List<Function> = emptyList()
   open fun methods(): List<Method> = emptyList()
   open fun files() = listOf("main.mus")

   override fun provideModule(interpreter: LanguageModuleInterpreter): Module {
      return provideNativeModule(interpreter).merge(provideLanguageModule(interpreter))
   }

   private fun provideNativeModule(interpreter: LanguageModuleInterpreter): Module {
      return object : NativeModuleProvider(path) {
         override fun functions() = this@HybridModuleProvider.functions()
         override fun methods() = this@HybridModuleProvider.methods()
      }.provideModule(interpreter)
   }

   // Disclaimer:
   // Unfortunately, LanguageModuleProvider cannot be reused here because
   // getResource() method returns null if is invoked from anonymous class's object instance.
   // Therefore it is need to have following code here.
   private fun provideLanguageModule(interpreter: LanguageModuleInterpreter): Module {
      val segments = path.split(".")
      val parentNodesChainPath = segments.dropLast(1).joinToString(".")
      val moduleName = segments.last()

      val module = files()
         .asSequence()
         .map { it to javaClass.classLoader.getResource(it) }
         .map { it.first to (it.second?.readText() ?: throw RuntimeException("Module '$path' does not contain '${it.first}' file")) }
         .map { interpreter.run(it.second, "module $path::${it.first}") }
         .map { it.getRootModule() }
         .reduce { acc, module -> acc.merge(module) }

      module.name = moduleName

      return Module.create(parentNodesChainPath, children = listOf(module))
   }
}