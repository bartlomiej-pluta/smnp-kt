package com.bartlomiejpluta.smnp.ext.mic.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.ext.mic.lib.loop.TestMicrophoneLevelLoop
import com.bartlomiejpluta.smnp.type.model.Value

class MicLevelFunction : Function("micLevel") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple() body { _, _ ->
         val printer = TestMicrophoneLevelLoop()
         printer.run()
         Value.void()
      }
   }
}