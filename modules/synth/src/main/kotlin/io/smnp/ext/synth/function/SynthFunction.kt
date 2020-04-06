package io.smnp.ext.synth.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.ext.synth.lib.synthesizer.Synthesizer
import io.smnp.ext.synth.lib.wave.Wave
import io.smnp.ext.synth.lib.wave.WaveCompiler
import io.smnp.type.enumeration.DataType.*
import io.smnp.type.matcher.Matcher.Companion.anyType
import io.smnp.type.matcher.Matcher.Companion.listOf
import io.smnp.type.matcher.Matcher.Companion.listOfMatchers
import io.smnp.type.matcher.Matcher.Companion.mapOfMatchers
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value

class SynthFunction : Function("synth") {

   override fun define(new: FunctionDefinitionTool) {
      new function simple(listOf(INT)) body { _, (wave) ->
         val bytes = (wave.value as List<Value>).map { (it.value as Int).toByte() }.toByteArray()
         Synthesizer.synth(Wave(bytes))
         Value.void()
      }

      new function Signature.vararg(
         listOfMatchers(ofType(NOTE), ofType(INT), mapOfMatchers(ofType(STRING), anyType())),
         mapOfMatchers(ofType(STRING), anyType())
      ) body { _, (config, vararg) ->
         val compiler = WaveCompiler(
            config,
            Synthesizer.SAMPLING_RATE
         )
         val wave = compiler.compileLines((vararg.value as List<Value>).map { it.value as List<Value> })
         Synthesizer.synth(wave)
         Value.void()
      }

      new function Signature.vararg(
         listOfMatchers(
            ofType(NOTE),
            ofType(INT),
            mapOfMatchers(ofType(STRING), anyType())
         )
      ) body { _, (vararg) ->
         val compiler = WaveCompiler(
            Value.map(emptyMap()),
            Synthesizer.SAMPLING_RATE
         )
         val wave = compiler.compileLines((vararg.value as List<Value>).map { it.value as List<Value> })
         Synthesizer.synth(wave)
         Value.void()
      }
   }
}