package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.ext.mic.SoundListenerLoop
import io.smnp.type.enumeration.DataType.INT
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value

class WaitFunction : Function("wait") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(ofType(INT), ofType(INT)) body { _, (inThreshold, outThreshold) ->
         val loop = SoundListenerLoop(inThreshold.value as Int, outThreshold.value as Int)
         loop.run()
         Value.void()
      }
   }
}