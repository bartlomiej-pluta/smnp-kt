package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature
import io.smnp.ext.synth.Synthesizer
import io.smnp.ext.synth.WaveCompiler
import io.smnp.type.enumeration.DataType.*
import io.smnp.type.matcher.Matcher.Companion.anyType
import io.smnp.type.matcher.Matcher.Companion.listOf
import io.smnp.type.matcher.Matcher.Companion.mapOfMatchers
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value

class WaveFunction : Function("wave") {

   override fun define(new: FunctionDefinitionTool) {
      new function Signature.vararg(
         listOf(NOTE, INT, STRING),
         mapOfMatchers(ofType(STRING), anyType())
      ) body { _, (config, vararg) ->
         val compiler = WaveCompiler(config, Synthesizer.SAMPLING_RATE)

         val wave = compiler.compileLines(vararg.unwrapCollections() as List<List<Value>>)

         Value.list(wave.bytes.map { Value.int(it.toInt()) }.toList())
      }

      new function Signature.vararg(listOf(NOTE, INT, STRING)) body { _, (vararg) ->
         val compiler = WaveCompiler(Value.map(emptyMap()), Synthesizer.SAMPLING_RATE)

         val wave = compiler.compileLines(vararg.unwrapCollections() as List<List<Value>>)

         Value.list(wave.bytes.map { Value.int(it.toInt()) }.toList())
      }
   }
}