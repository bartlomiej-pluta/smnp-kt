package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature
import io.smnp.ext.synth.AdsrEnvelope
import io.smnp.ext.synth.Synthesizer
import io.smnp.ext.synth.WaveCompiler
import io.smnp.type.enumeration.DataType
import io.smnp.type.matcher.Matcher
import io.smnp.type.model.Value

class WaveFunction : Function("wave") {
   override fun define(new: FunctionDefinitionTool) {
      new function Signature.vararg(Matcher.listOf(DataType.NOTE, DataType.INT, DataType.STRING)) body { _, (vararg) ->
         val compiler = WaveCompiler(
            listOf(1.0, 0.5),
            AdsrEnvelope(0.1, 0.3, 0.7, 0.8),
            120,
            440.0,
            Synthesizer.SAMPLING_RATE
         )

         val wave = compiler.compileLines(vararg.unwrapCollections() as List<List<Value>>)

         Value.list(wave.bytes.map { Value.int(it.toInt()) }.toList())
      }
   }
}