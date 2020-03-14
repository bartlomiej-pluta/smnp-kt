package io.smnp.environment

import io.smnp.callable.function.Function
import io.smnp.callable.method.Method
import io.smnp.callable.signature.ActualSignatureFormatter.format
import io.smnp.error.EvaluationException
import io.smnp.ext.DefaultModuleRegistry.requestModuleProviderForPath
import io.smnp.ext.ModuleProvider
import io.smnp.interpreter.LanguageModuleInterpreter
import io.smnp.runtime.model.CallStack
import io.smnp.type.model.Value
import io.smnp.type.module.Module

class DefaultEnvironment : Environment {
   private val rootModule = Module.create("<root>")
   private val loadedModules = mutableListOf<String>()
   private val callStack = CallStack()

   init {
      callStack.push(rootModule, "<entrypoint>", emptyList())
   }

   override fun loadModule(path: String) {
      requestModuleProviderForPath(path).let {
         loadModule(it)
         loadDependencies(it)
      }
   }

   private fun loadModule(moduleProvider: ModuleProvider, consumer: (ModuleProvider) -> Unit = {}) {
      if (!loadedModules.contains(moduleProvider.path)) {
         rootModule.addSubmodule(moduleProvider.provideModule(LanguageModuleInterpreter()))
         moduleProvider.onModuleLoad(this)
         loadedModules.add(moduleProvider.path)
         consumer(moduleProvider)
      }
   }

   private fun loadDependencies(moduleProvider: ModuleProvider) {
      moduleProvider.dependencies().forEach { dependency ->
         loadModule(requestModuleProviderForPath(dependency)) {
            loadDependencies(it)
         }
      }
   }

   override fun printModules(printContent: Boolean) {
      rootModule.pretty(printContent)
   }

   override fun invokeFunction(name: String, arguments: List<Value>): Value {
      val foundFunctions = rootModule.findFunction(name)
      if (foundFunctions.isEmpty()) {
         throw EvaluationException("No function found with name of '$name'")
      }

      if (foundFunctions.size > 1) {
         throw EvaluationException("Found ${foundFunctions.size} functions with name of $name: [${foundFunctions.map { it.module.canonicalName }.joinToString()}]")

      }

      val function = foundFunctions[0]

      callStack.push(function.module, function.name, arguments)
      val value = function.call(this, *arguments.toTypedArray())
      callStack.pop()

      return value
   }

   override fun invokeMethod(obj: Value, name: String, arguments: List<Value>): Value {
      val foundMethods = rootModule.findMethod(obj, name)
      if (foundMethods.isEmpty()) {
         throw EvaluationException(
            "No method found with name of '$name' for ${format(
               obj
            )}"
         )
      }

      if (foundMethods.size > 1) {
         throw EvaluationException(
            "Found ${foundMethods.size} methods with name of $name for ${format(
               obj
            )}: [${foundMethods.map { it.module.canonicalName }.joinToString()}]"
         )
      }

      val method = foundMethods[0]

      callStack.push(method.module, "${method.typeMatcher}.${method.name}", arguments)
      val value = method.call(this, obj, *arguments.toTypedArray())
      callStack.pop()

      return value
   }

   override fun printCallStack() {
      callStack.pretty()
   }

   override fun stackTrace(): String {
      return callStack.stackTrace();
   }

   override fun defineFunction(function: Function) {
      rootModule.addFunction(function)
   }

   override fun defineMethod(method: Method) {
      rootModule.addMethod(method)
   }

   override fun pushScope(scope: MutableMap<String, Value>) = callStack.top().pushScope(scope)

   override fun popScope() = callStack.top().popScope()

   override fun printScopes() = callStack.top().prettyScope()

   override fun setVariable(name: String, value: Value) = callStack.top().setVariable(name, value)

   override fun getVariable(name: String) = callStack.top().getVariable(name)

   override fun getRootModule() = rootModule
}