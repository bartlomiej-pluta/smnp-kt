package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature
import io.smnp.type.model.Value

class CallStackFunction : Function("callstack") {
   override fun define(new: FunctionDefinitionTool) {
      new function Signature.simple() body { env, _ ->
         env.printCallStack()
         Value.void()
      }
   }
}