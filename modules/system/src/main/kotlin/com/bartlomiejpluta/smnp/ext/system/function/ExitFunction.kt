package com.bartlomiejpluta.smnp.ext.system.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.type.enumeration.DataType.INT
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.optional
import com.bartlomiejpluta.smnp.type.model.Value
import kotlin.system.exitProcess

class ExitFunction : Function("exit") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(optional(ofType(INT))) body { _, arguments ->
         val exitCode = arguments.getOrNull(0) ?: Value.int(0)
         exitProcess(exitCode.value as Int)
      }
   }
}