package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature
import io.smnp.type.enumeration.DataType.BOOL
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.matcher.Matcher.Companion.optional
import io.smnp.type.model.Value

class StackTraceFuction : Function("stacktrace") {
   override fun define(new: FunctionDefinitionTool) {
      new function Signature.simple(optional(ofType(BOOL))) body { env, args ->
         env.printCallStack(args.getOrNull(0)?.value as Boolean? ?: false)
         Value.void()
      }
   }
}