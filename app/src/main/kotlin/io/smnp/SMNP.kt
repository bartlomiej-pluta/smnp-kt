package io.smnp

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import io.smnp.cli.model.entity.Arguments
import io.smnp.cli.model.enumeration.ModulesPrintMode
import io.smnp.environment.DefaultEnvironment
import io.smnp.ext.DefaultModuleRegistry
import io.smnp.interpreter.DefaultInterpreter
import io.smnp.preset.PresetProvider.providePresetCode
import io.smnp.type.model.Value

fun main(args: Array<String>): Unit = mainBody {
   ArgParser(args).parseInto(::Arguments).run {
      val interpreter = DefaultInterpreter()
      val environment = DefaultEnvironment()

      environment.setVariable("__param__", Value.wrap(parameters.toMap()))

      when {
         file != null -> interpreter.run(file!!, environment, printTokens, printAst, dryRun)
         code != null -> interpreter.run(code!!, environment, printTokens, printAst, dryRun)
         preset != null -> interpreter.run(providePresetCode(preset!!), environment, printTokens, printAst, dryRun)
         else -> null
      }?.let { it as DefaultEnvironment }?.let { disposedEnvironment ->
         if(loadedModules != null) {
            println("Loaded modules:")
            when (loadedModules) {
               ModulesPrintMode.LIST -> disposedEnvironment.modules.forEach { println(it) }
               ModulesPrintMode.TREE -> disposedEnvironment.printModules(false)
               ModulesPrintMode.CONTENT -> disposedEnvironment.printModules(true)
            }
         }
      }

      if (availableModules) {
         println("Available modules:")
         DefaultModuleRegistry.registeredModules().forEach { println(it) }
      }
   }
}