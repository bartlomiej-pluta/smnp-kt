package com.bartlomiejpluta.smnp.ext.synth.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.ext.synth.lib.synthesizer.Synthesizer
import com.bartlomiejpluta.smnp.ext.synth.lib.wave.Wave
import com.bartlomiejpluta.smnp.ext.synth.lib.wave.WaveCompiler
import com.bartlomiejpluta.smnp.type.enumeration.DataType.*
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.anyType
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.listOf
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.listOfMatchers
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.mapOfMatchers
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.model.Value

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