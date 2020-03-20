package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.ext.synth.Synthesizer
import io.smnp.ext.synth.Wave
import io.smnp.type.enumeration.DataType.INT
import io.smnp.type.matcher.Matcher.Companion.listOf
import io.smnp.type.model.Value

class SynthFunction : Function("synth") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(listOf(INT)) body { _, (wave) ->
         val bytes = (wave.value as List<Value>).map { (it.value as Int).toByte() }.toByteArray()
         Synthesizer.synth(Wave(bytes))
         Value.void()
      }
   }
}