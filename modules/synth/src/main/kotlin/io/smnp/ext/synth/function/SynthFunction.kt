package io.smnp.ext.synth.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.ext.synth.lib.synthesizer.Synthesizer
import io.smnp.ext.synth.lib.wave.Wave
import io.smnp.ext.synth.lib.wave.WaveCompiler
import io.smnp.type.enumeration.DataType
import io.smnp.type.enumeration.DataType.INT
import io.smnp.type.matcher.Matcher
import io.smnp.type.matcher.Matcher.Companion.listOf
import io.smnp.type.model.Value

class SynthFunction : Function("synth") {

   override fun define(new: FunctionDefinitionTool) {
      new function simple(listOf(INT)) body { _, (wave) ->
         val bytes = (wave.value as List<Value>).map { (it.value as Int).toByte() }.toByteArray()
         Synthesizer.synth(Wave(bytes))
         Value.void()
      }

      new function Signature.vararg(
         listOf(DataType.NOTE, INT, DataType.STRING),
         Matcher.mapOfMatchers(Matcher.ofType(DataType.STRING), Matcher.anyType())
      ) body { _, (config, vararg) ->
         val compiler = WaveCompiler(
            config,
            Synthesizer.SAMPLING_RATE
         )
         val wave = compiler.compileLines(vararg.unwrapCollections() as List<List<Value>>)
         Synthesizer.synth(wave)
         Value.void()
      }

      new function Signature.vararg(listOf(DataType.NOTE, INT, DataType.STRING)) body { _, (vararg) ->
         val compiler = WaveCompiler(
            Value.map(emptyMap()),
            Synthesizer.SAMPLING_RATE
         )
         val wave = compiler.compileLines(vararg.unwrapCollections() as List<List<Value>>)
         Synthesizer.synth(wave)
         Value.void()
      }
   }
}