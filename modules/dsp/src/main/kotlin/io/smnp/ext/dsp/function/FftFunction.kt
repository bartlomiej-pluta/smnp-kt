package io.smnp.ext.dsp.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.ext.dsp.lib.fft.FourierTransform.fft
import io.smnp.type.enumeration.DataType.FLOAT
import io.smnp.type.enumeration.DataType.INT
import io.smnp.type.matcher.Matcher.Companion.listOf
import io.smnp.type.model.Value

class FftFunction : Function("fft") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(listOf(INT, FLOAT)) body { _, (signal) ->
         val x = (signal.unwrap() as List<Number>).map { it.toDouble() }
         Value.list(fft(x).map { Value.float(it.mod.toFloat()) })
      }
   }
}