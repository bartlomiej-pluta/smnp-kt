package com.bartlomiejpluta.smnp.ext.math.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.type.model.Value
import kotlin.random.Random

class RandomFunction : Function("random") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple() body { _, _ ->
         Value.float(Random.nextFloat())
      }
   }
}