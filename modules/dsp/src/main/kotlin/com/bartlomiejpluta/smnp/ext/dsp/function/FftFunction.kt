package com.bartlomiejpluta.smnp.ext.dsp.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.ext.dsp.lib.fft.FourierTransform.fft
import com.bartlomiejpluta.smnp.type.enumeration.DataType.FLOAT
import com.bartlomiejpluta.smnp.type.enumeration.DataType.INT
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.listOf
import com.bartlomiejpluta.smnp.type.model.Value

class FftFunction : Function("fft") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(listOf(INT, FLOAT)) body { _, (signal) ->
         val x = (signal.unwrap() as List<Number>).map { it.toDouble() }
         Value.list(fft(x).map { Value.float(it.mod.toFloat()) })
      }
   }
}