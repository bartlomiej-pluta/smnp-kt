package io.smnp

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import io.smnp.cli.model.entity.Arguments
import io.smnp.cli.model.enumeration.ModulesPrintMode
import io.smnp.environment.DefaultEnvironment
import io.smnp.ext.DefaultModuleRegistry
import io.smnp.interpreter.DefaultInterpreter

fun main(args: Array<String>): Unit = mainBody {
   ArgParser(args).parseInto(::Arguments).run {
      val interpreter = DefaultInterpreter()

      when {
         file != null -> interpreter.run(file!!, printTokens, printAst, dryRun)
         code != null -> interpreter.run(code!!, printTokens, printAst, dryRun)
         else -> null
      }?.let { it as DefaultEnvironment }?.let { environment ->
         if(loadedModules != null) {
            println("Loaded modules:")
            when (loadedModules) {
               ModulesPrintMode.LIST -> environment.modules.forEach { println(it) }
               ModulesPrintMode.TREE -> environment.printModules(false)
               ModulesPrintMode.CONTENT -> environment.printModules(true)
            }
         }
      }

      if (availableModules) {
         println("Available modules:")
         DefaultModuleRegistry.registeredModules().forEach { println(it) }
      }
   }
}