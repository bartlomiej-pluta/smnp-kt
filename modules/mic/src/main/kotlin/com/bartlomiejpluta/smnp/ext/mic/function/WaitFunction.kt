package com.bartlomiejpluta.smnp.ext.mic.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.ext.mic.lib.loop.SoundListenerLoop
import com.bartlomiejpluta.smnp.type.enumeration.DataType.INT
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.model.Value

class WaitFunction : Function("wait") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(ofType(INT), ofType(INT)) body { _, (inThreshold, outThreshold) ->
         val loop = SoundListenerLoop(
            inThreshold.value as Int,
            outThreshold.value as Int
         )
         loop.run()
         Value.void()
      }
   }
}