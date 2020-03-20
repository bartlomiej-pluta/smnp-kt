package io.smnp.ext.synth

import io.smnp.data.entity.Note
import io.smnp.data.enumeration.Pitch
import io.smnp.error.CustomException
import io.smnp.math.Fraction
import io.smnp.type.enumeration.DataType
import io.smnp.type.model.Value
import kotlin.math.pow

class WaveCompiler(
   private val overtones: List<Double>,
   private val envelope: Envelope,
   private val bpm: Int,
   tuning: Double,
   private val samplingRate: Double
) {
   private val semitone = 2.0.pow(1.0/12.0)
   private val tuningTable = Pitch.values().mapIndexed { index, pitch -> pitch to tuning/semitone.pow(57-index) }.toMap()

   fun compileLines(lines: List<List<Value>>): Wave {
      return Wave.merge(*lines.map { compileLine(it) }.toTypedArray())
   }

   fun compileLine(line: List<Value>): Wave {
      return line.fold(Wave.EMPTY) { acc, value ->
         acc + when(value.type) {
            DataType.NOTE -> compileNote(value.value as Note)
            DataType.INT -> compileRest(value.value as Int)
            DataType.STRING -> Wave.EMPTY
            else -> throw CustomException("Invalid data type: '${value.typeName}")
         }
      }
   }

   private fun compileNote(note: Note) = sound((tuningTable[note.pitch] ?: error("")) * 2.0.pow(note.octave), duration(note.duration))

   private fun duration(duration: Fraction) = 60.0 * 4.0 * duration.decimal / bpm

   private fun compileRest(rest: Int) = Wave.zeros(duration(Fraction(1, rest)), samplingRate)

   private fun sound(
      frequency: Double,
      duration: Double
   ): Wave {
      val wave = Wave.merge(*overtones.mapIndexed { overtone, ratio ->
         Wave.sine(frequency * (overtone + 1), duration, samplingRate) * ratio
      }.toTypedArray())

      return envelope.apply(wave)
   }
}