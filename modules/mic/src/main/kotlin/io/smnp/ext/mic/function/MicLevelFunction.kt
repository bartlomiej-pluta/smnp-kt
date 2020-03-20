package io.smnp.ext.mic.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.ext.mic.lib.loop.TestMicrophoneLevelLoop
import io.smnp.type.model.Value

class MicLevelFunction : Function("micLevel") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple() body { _, _ ->
         val printer = TestMicrophoneLevelLoop()
         printer.run()
         Value.void()
      }
   }
}