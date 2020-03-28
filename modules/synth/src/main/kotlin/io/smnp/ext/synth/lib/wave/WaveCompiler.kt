package io.smnp.ext.synth.lib.wave

import io.smnp.data.entity.Note
import io.smnp.data.enumeration.Pitch
import io.smnp.error.CustomException
import io.smnp.ext.synth.lib.envelope.Envelope
import io.smnp.ext.synth.lib.envelope.EnvelopeFactory
import io.smnp.math.Fraction
import io.smnp.type.enumeration.DataType
import io.smnp.type.matcher.Matcher
import io.smnp.type.model.Value
import io.smnp.util.config.ConfigMapSchema
import kotlin.math.pow

class WaveCompiler(config: Value, private val samplingRate: Double) {
   private val semitone = 2.0.pow(1.0 / 12.0)
   private val schema = ConfigMapSchema()
      .optional("bpm", Matcher.ofType(DataType.INT), Value.int(120))
      .optional(
         "overtones", Matcher.listOf(DataType.FLOAT), Value.list(
            listOf(
               Value.float(1.0F),
               Value.float(0.7F),
               Value.float(0.5F),
               Value.float(0.3F)
            )
         )
      )
      .optional("tuning", Matcher.ofType(DataType.FLOAT), Value.float(440.0F))
      .optional(
         "envelope", Matcher.mapOfMatchers(Matcher.ofType(DataType.STRING), Matcher.anyType()), Value.wrap(
            mapOf(
               "name" to "adsr",
               "p1" to 0.1F,
               "p2" to 0.3F,
               "p3" to 0.8F,
               "s" to 0.8F
            )
         )
      )

   private val bpm: Int
   private val envelope: Envelope
   private val tuningTable: Map<Pitch, Double>
   private val overtones: List<Double>

   init {
      schema.parse(config).let { configMap ->
         envelope = EnvelopeFactory.provideEnvelope(configMap["envelope"])
         overtones = (configMap["overtones"].unwrap() as List<Float>).map { it.toDouble() }
         bpm = configMap["bpm"].value as Int
         tuningTable = Pitch.values()
            .mapIndexed { index, pitch -> pitch to (configMap["tuning"].value as Float).toDouble() / semitone.pow(57 - index) }
            .toMap()
      }
   }

   fun compileLines(lines: List<List<Value>>): Wave {
      return Wave.merge(*lines.map { compileLine(it) }.toTypedArray())
   }

   private fun compileLine(line: List<Value>): Wave {
      return line.fold(Wave.EMPTY) { acc, value ->
         acc + when (value.type) {
            DataType.NOTE -> compileNote(value.value as Note)
            DataType.INT -> compileRest(value.value as Int)
            DataType.STRING -> Wave.EMPTY
            else -> throw CustomException("Invalid data type: '${value.typeName}")
         }
      }
   }

   private fun compileNote(note: Note) =
      sound((tuningTable[note.pitch] ?: error("")) * 2.0.pow(note.octave), duration(note.duration))

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