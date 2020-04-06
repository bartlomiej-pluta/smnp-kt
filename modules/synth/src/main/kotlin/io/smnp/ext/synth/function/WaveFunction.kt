package io.smnp.ext.synth.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature
import io.smnp.ext.synth.lib.synthesizer.Synthesizer
import io.smnp.ext.synth.lib.wave.WaveCompiler
import io.smnp.type.enumeration.DataType.*
import io.smnp.type.matcher.Matcher.Companion.anyType
import io.smnp.type.matcher.Matcher.Companion.listOfMatchers
import io.smnp.type.matcher.Matcher.Companion.mapOfMatchers
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value

class WaveFunction : Function("wave") {

   override fun define(new: FunctionDefinitionTool) {
      new function Signature.vararg(
         listOfMatchers(ofType(NOTE), ofType(INT), mapOfMatchers(ofType(STRING), anyType())),
         mapOfMatchers(ofType(STRING), anyType())
      ) body { _, (config, vararg) ->
         val compiler = WaveCompiler(config, Synthesizer.SAMPLING_RATE)

         val wave = compiler.compileLines((vararg.value as List<Value>).map { it.value as List<Value> })

         Value.list(wave.bytes.map { Value.int(it.toInt()) }.toList())
      }

      new function Signature.vararg(listOfMatchers(ofType(NOTE), ofType(INT), mapOfMatchers(ofType(STRING), anyType()))) body { _, (vararg) ->
         val compiler = WaveCompiler(Value.map(emptyMap()), Synthesizer.SAMPLING_RATE)

         val wave = compiler.compileLines((vararg.value as List<Value>).map { it.value as List<Value> })

         Value.list(wave.bytes.map { Value.int(it.toInt()) }.toList())
      }
   }
}