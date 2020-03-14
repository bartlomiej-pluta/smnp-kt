package io.smnp.environment

import io.smnp.callable.function.Function
import io.smnp.callable.method.Method
import io.smnp.callable.signature.ActualSignatureFormatter.format
import io.smnp.error.FunctionInvocationException
import io.smnp.error.MethodInvocationException
import io.smnp.ext.DefaultModuleRegistry
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
   var disposed = false
      private set

   val modules: List<String>
   get() = loadedModules

   init {
      callStack.push(rootModule, "<entrypoint>", emptyList())
   }

   override fun loadModule(path: String) {
      assertDisposal()
      requestModuleProviderForPath(path).let {
         loadModule(it)
         loadDependencies(it)
      }
   }

   private fun assertDisposal() {
      if(disposed) {
         throw RuntimeException("Disposed environment is immutable and cannot be further used as environment for evaluating program")
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
      assertDisposal()
      val foundFunctions = rootModule.findFunction(name)
      if (foundFunctions.isEmpty()) {
         throw FunctionInvocationException("No function found with name of '$name'")
      }

      val matchedFunctions = foundFunctions.filter { it.matches(arguments) }
      if (matchedFunctions.size > 1) {
         throw FunctionInvocationException("Found ${matchedFunctions.size} functions with name of $name, that matched provided arguments: [${matchedFunctions.joinToString { it.module.canonicalName }}]")
      }

      if(matchedFunctions.isEmpty()) {
         var message = "No function matches following signature:\n   $name${format(arguments.toTypedArray())}"
         if(foundFunctions.isNotEmpty()) {
            message += "\nDid you mean:\n"
            message += foundFunctions.flatMap { it.signature }.joinToString("\n") { "   $it" }
         }
         throw FunctionInvocationException(message)
      }

      val function = matchedFunctions[0]

      callStack.push(function.module, function.name, arguments)
      val value = function.call(this, *arguments.toTypedArray())
      callStack.pop()

      return value
   }

   override fun invokeMethod(obj: Value, name: String, arguments: List<Value>): Value {
      assertDisposal()
      val foundMethods = rootModule.findMethod(obj, name)
      if (foundMethods.isEmpty()) {
         throw MethodInvocationException(
            "No method found with name of '$name' for ${format(
               obj
            )}"
         )
      }

      val matchedMethods = foundMethods.filter { it.matches(obj, arguments) }
      if (matchedMethods.size > 1) {
         throw MethodInvocationException("Found ${matchedMethods.size} functions with name of $name, that matched provided arguments: [${matchedMethods.joinToString { it.module.canonicalName }}]")
      }

      if(matchedMethods.isEmpty()) {
         var message = "No method matches following signature:\n   ${format(obj)}.$name${format(arguments.toTypedArray())}"
         if(foundMethods.isNotEmpty()) {
            message += "\nDid you mean:\n"
            message += foundMethods.flatMap { it.signature }.joinToString("\n") { "   $it" }
         }
         throw FunctionInvocationException(message)
      }

      val method = matchedMethods[0]

      callStack.push(method.module, "${method.typeMatcher}.${method.name}", arguments)
      val value = method.call(this, obj, *arguments.toTypedArray())
      callStack.pop()

      return value
   }

   override fun printCallStack(scopes: Boolean) {
      callStack.pretty(scopes)
   }

   override fun stackTrace(): String {
      return callStack.stackTrace.joinToString("\n")
   }

   override fun defineFunction(function: Function) {
      assertDisposal()
      rootModule.addFunction(function)
   }

   override fun defineMethod(method: Method) {
      assertDisposal()
      rootModule.addMethod(method)
   }

   override fun pushScope(scope: MutableMap<String, Value>) {
      assertDisposal()
      callStack.top().pushScope(scope)
   }

   override fun popScope(): MutableMap<String, Value>? {
      assertDisposal()
      return callStack.top().popScope()
   }

   override fun printScopes() = callStack.top().prettyScope()

   override fun setVariable(name: String, value: Value) {
      assertDisposal()
      callStack.top().setVariable(name, value)
   }

   override fun getVariable(name: String) = callStack.top().getVariable(name)

   override fun dispose() {
      assertDisposal()
      disposed = true
      DefaultModuleRegistry.disposeModules(this)
   }

   override fun getRootModule() = rootModule

   override fun toString(): String {
      var string = "Default environment ${if(disposed) "[DISPOSED]" else ""}\n"
      string += "- loaded modules: ${loadedModules.size}\n"
      string += "- call stack frames: ${callStack.size}\n"
      string += "- top frame scopes: ${callStack.top().scopesCount}\n"
      return string
   }
}