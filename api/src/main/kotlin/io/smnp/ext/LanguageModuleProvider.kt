package io.smnp.ext

import io.smnp.interpreter.LanguageModuleInterpreter
import io.smnp.type.module.Module

abstract class LanguageModuleProvider(path: String) : ModuleProvider(path) {
   open fun files() = listOf("main.mus")

   override fun provideModule(interpreter: LanguageModuleInterpreter): Module {
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