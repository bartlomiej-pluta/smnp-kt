package io.smnp.ext.synth.lib.wave

import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt
import kotlin.math.sin

class Wave(val bytes: ByteArray) {

   operator fun plus(wave: Wave): Wave {
      val stream = ByteArrayOutputStream()
      stream.writeBytes(bytes)
      stream.writeBytes(wave.bytes)
      return Wave(stream.toByteArray())
   }

   operator fun times(value: Double): Wave {
      return Wave(bytes.map { (it * value).toByte() }.toByteArray())
   }

   fun extend(bytes: ByteArray): Wave {
      return this + Wave(bytes)
   }

   companion object {
      val EMPTY = Wave(ByteArray(0))

      fun merge(vararg waves: Wave): Wave {
         val longestWaveSize = waves.maxBy { it.bytes.size }?.let { it.bytes.size }
            ?: throw RuntimeException("Empty waves are not supported")
         val adjustedWaves = waves.map { it.extend(ByteArray(longestWaveSize - it.bytes.size)) }

         val signal = IntRange(0, longestWaveSize-1).map { index ->
            adjustedWaves.map { it.bytes[index].toDouble() }.sum()
         }

         val maxValue = Byte.MAX_VALUE.toDouble() / (signal.max() ?: 1.0)

         return Wave(signal.map { (it * maxValue).toByte() }.toByteArray())
      }

      fun sine(frequency: Double, duration: Double, samplingRate: Double): Wave {
         val length = (samplingRate * duration).roundToInt()
         val buffer = ByteArray(length)

         buffer.forEachIndexed { i, _ ->
            buffer[i] = (Byte.MAX_VALUE * sin(i / samplingRate * 2.0 * Math.PI * frequency)).toByte()
         }

         return Wave(buffer)
      }

      fun zeros(duration: Double, samplingRate: Double): Wave {
         return Wave(ByteArray((samplingRate * duration).roundToInt()))
      }
   }
}