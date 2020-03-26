package io.smnp.ext.dsp.lib.fft

import io.smnp.ext.dsp.lib.complex.Complex
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.pow

object FourierTransform {

   fun fft(signal: List<Double>): List<Complex> {
      return radix2(padSignal(signal).map { Complex(it, 0.0) })
   }

   private fun padSignal(signal: List<Double>): List<Double> {
      val totalSignalSize = 2.0.pow(nextPow2(signal.size.toDouble()))
      val paddingSize = (totalSignalSize - signal.size).toInt()
      return signal + List(paddingSize) { 0.0 }
   }

   private fun nextPow2(x: Double) = ceil(log(abs(x), 2.0))

   private fun radix2(signal: List<Complex>): List<Complex> {
      val N = signal.size

      if(N == 1) return listOf(signal[0])

      if(N % 2 != 0) throw IllegalArgumentException("The number of samples should be power of 2")

      val even = radix2(signal.filterIndexed { index, _ -> index % 2 == 0 })
      val odd = radix2(signal.filterIndexed { index, _ -> index % 2 != 0 })

      val output = MutableList(N) { Complex(0.0, 0.0) }
      even.zip(odd).forEachIndexed { k, (e, o) ->
         val z = Complex(-2 * k * Math.PI / N)
         output[k] = e + z * o
         output[k+N/2] = e - z * o
      }

      return output
   }
}