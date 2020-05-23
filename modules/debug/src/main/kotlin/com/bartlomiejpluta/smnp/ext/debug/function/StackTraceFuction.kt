package com.bartlomiejpluta.smnp.ext.debug.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature
import com.bartlomiejpluta.smnp.type.enumeration.DataType.BOOL
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.optional
import com.bartlomiejpluta.smnp.type.model.Value

class StackTraceFuction : Function("stacktrace") {
   override fun define(new: FunctionDefinitionTool) {
      new function Signature.simple(optional(ofType(BOOL))) body { env, args ->
         env.printCallStack(args.getOrNull(0)?.value as Boolean? ?: false)
         Value.void()
      }
   }
}