package io.smnp.ext.math.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.type.model.Value
import kotlin.random.Random

class RandomFunction : Function("random") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple() body { _, _ ->
         Value.float(Random.nextFloat())
      }
   }
}