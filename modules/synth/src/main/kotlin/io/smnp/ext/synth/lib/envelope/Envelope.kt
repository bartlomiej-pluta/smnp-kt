package io.smnp.ext.synth.lib.envelope

import io.smnp.ext.synth.lib.wave.Wave
import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper

abstract class Envelope {
   abstract fun eval(x: Double, length: Int): Double
   protected abstract fun name(): String

   fun plot() {
      val max = 100
      val x = IntRange(0, max).map { it.toDouble() }
      val chart = QuickChart.getChart(
         name(),
         "x",
         "y",
         "y(x)",
         x,
         x.map { eval(it, max) }
      )

      SwingWrapper(chart).displayChart()
   }

   fun apply(wave: Wave): Wave {
      return Wave(wave.bytes.mapIndexed { index, byte ->
         (byte * eval(
            index.toDouble(),
            wave.bytes.size
         )).toByte()
      }.toByteArray())
   }

   protected fun quad(a: Pair<Double, Double>, b: Pair<Double, Double>, c: Pair<Double, Double>): (Double) -> Double {
      return { x ->
         (a.second * ((x - b.first) / (a.first - b.first)) * ((x - c.first) / (a.first - c.first))) + (b.second * ((x - a.first) / (b.first - a.first)) * ((x - c.first) / (b.first - c.first))) + (c.second * ((x - a.first) / (c.first - a.first)) * ((x - b.first) / (c.first - b.first)))
      }
   }
}