package com.bartlomiejpluta.smnp

import com.bartlomiejpluta.smnp.cli.model.entity.Arguments
import com.bartlomiejpluta.smnp.cli.model.enumeration.ModulesPrintMode
import com.bartlomiejpluta.smnp.environment.DefaultEnvironment
import com.bartlomiejpluta.smnp.error.EnvironmentException
import com.bartlomiejpluta.smnp.error.PositionException
import com.bartlomiejpluta.smnp.error.SmnpException
import com.bartlomiejpluta.smnp.ext.registry.DefaultModuleRegistry
import com.bartlomiejpluta.smnp.interpreter.DefaultInterpreter
import com.bartlomiejpluta.smnp.preset.PresetProvider.providePresetCode
import com.bartlomiejpluta.smnp.type.model.Value
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import kotlin.system.exitProcess

fun main(args: Array<String>): Unit = mainBody {
   ArgParser(args).parseInto(::Arguments).run {
      val interpreter = DefaultInterpreter()
      val environment = DefaultEnvironment()

      try {
         environment.setVariable("__param__", Value.wrap(parameters.toMap()))

         when {
            file != null -> interpreter.run(file!!, environment, printTokens, printAst, dryRun)
            code != null -> interpreter.run(code!!, environment, printTokens, printAst, dryRun)
            preset != null -> interpreter.run(providePresetCode(preset!!), environment, printTokens, printAst, dryRun)
            else -> null
         }?.let { it as DefaultEnvironment }?.let { disposedEnvironment ->
            if (loadedModules != null) {
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
      } catch (e: SmnpException) {
         System.err.println(e.friendlyName)
         e.exceptionChain.mapNotNull { it as? PositionException }.lastOrNull()?.let { System.err.println(it.position.fullString) }
         System.err.println()
         System.err.println(e.message)
         e.exceptionChain.mapNotNull { it as? EnvironmentException }.lastOrNull()?.let {
            System.err.println("\nStack trace:\n${it.environment.stackTrace()}")
         }
         exitProcess(1)
      } finally {
         if(!environment.disposed) {
            environment.dispose()
         }
      }
   }
}