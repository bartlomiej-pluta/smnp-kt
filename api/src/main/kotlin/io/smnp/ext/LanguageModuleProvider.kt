package io.smnp.ext

import io.smnp.interpreter.Interpreter
import io.smnp.type.module.Module

abstract class LanguageModuleProvider(path: String) : ModuleProvider(path) {
   open fun files() = listOf("main.mus")

   override fun provideModule(interpreter: Interpreter): Module {
      val module = Module.create(path)
      interpreter.updateRootModule(module)

      files()
         .map { javaClass.classLoader.getResource(it) }
         .map { it.readText() }
         .forEach { interpreter.run(it) }

      return module
   }
}