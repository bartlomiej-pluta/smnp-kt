package com.bartlomiejpluta.smnp.callable.method

import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.type.matcher.Matcher
import com.bartlomiejpluta.smnp.type.model.Value
import com.bartlomiejpluta.smnp.type.module.Module

abstract class Method(val typeMatcher: Matcher, val name: String) {
   private var definitions: List<MethodDefinition> = mutableListOf()
   private var _module: Module? = null
   var module: Module
      get() = _module ?: throw RuntimeException("Method has not set module yet")
      set(value) {
         _module = value
      }

   abstract fun define(new: MethodDefinitionTool)

   init {
      definitions = MethodDefinitionTool().apply { define(this) }.definitions
   }

   fun verifyType(type: Value) = typeMatcher.match(type)

   fun matches(obj: Value, arguments: List<Value>) = verifyType(obj) && matchArguments(arguments) != null

   private fun matchArguments(arguments: List<Value>) = definitions
      .map { Pair(it, it.signature.parse(arguments.toList())) }
      .firstOrNull { (_, args) -> args.signatureMatched }

   fun call(environment: Environment, obj: Value, vararg arguments: Value): Value {
      val (definition, args) = matchArguments(arguments.toList())!! // todo?

      return definition.body(environment, obj, args.arguments)
   }

   val signature = definitions.map { "$typeMatcher.$name${it.signature}" }
}