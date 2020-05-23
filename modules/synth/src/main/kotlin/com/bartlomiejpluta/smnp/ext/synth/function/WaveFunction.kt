package com.bartlomiejpluta.smnp.ext.synth.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature
import com.bartlomiejpluta.smnp.ext.synth.lib.synthesizer.Synthesizer
import com.bartlomiejpluta.smnp.ext.synth.lib.wave.WaveCompiler
import com.bartlomiejpluta.smnp.type.enumeration.DataType.*
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.anyType
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.listOfMatchers
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.mapOfMatchers
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.model.Value

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