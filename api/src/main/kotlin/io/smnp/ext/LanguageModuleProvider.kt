package io.smnp.ext

import io.smnp.interpreter.Interpreter
import io.smnp.type.module.Module

abstract class LanguageModuleProvider(path: String) : ModuleProvider(path) {
   open fun files() = listOf("main.mus")

   override fun provideModule(interpreter: Interpreter): Module {
      val segments = path.split(".")
      val parentNodesChainPath = segments.dropLast(1).joinToString(".")
      val moduleName = segments.last()

      val module = files()
         .asSequence()
         .map { javaClass.classLoader.getResource(it) }
         .map { it.readText() }
         .map { interpreter.run(it) }
         .map { it.getRootModule() }
         .reduce { acc, module -> acc.merge(module) }

      module.name = moduleName

      return Module.create(parentNodesChainPath, children = listOf(module))
   }
}