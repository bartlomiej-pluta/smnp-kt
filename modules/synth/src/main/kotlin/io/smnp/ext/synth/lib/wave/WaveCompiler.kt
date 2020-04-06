package io.smnp.ext.synth.lib.wave

import io.smnp.data.entity.Note
import io.smnp.error.CustomException
import io.smnp.ext.synth.lib.model.CompilationParameters
import io.smnp.math.Fraction
import io.smnp.type.enumeration.DataType
import io.smnp.type.matcher.Matcher
import io.smnp.type.model.Value
import io.smnp.util.config.ConfigMapSchema
import kotlin.math.pow

class WaveCompiler(config: Value, private val samplingRate: Double) {
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

   private val globalConfig = schema.parse(config)

   fun compileLines(lines: List<List<Value>>): Wave {
      return Wave.merge(*lines.parallelStream().map { compileLine(it) }.toArray { Array(it) { Wave.EMPTY } })
   }

   private fun compileLine(line: List<Value>): Wave {
      var config = globalConfig
      var parameters = CompilationParameters(config)

      return line.fold(Wave.EMPTY) { acc, value ->
         acc + when (value.type) {
            DataType.NOTE -> compileNote(value.value as Note, parameters)
            DataType.INT -> compileRest(value.value as Int, parameters)
            DataType.MAP -> {
               config = schema.parse(value, config, globalConfig)
               parameters = CompilationParameters(config)
               Wave.EMPTY
            }
            else -> throw CustomException("Invalid data type: '${value.typeName}")
         }
      }
   }

   private fun compileNote(note: Note, parameters: CompilationParameters) =
      sound((parameters.tuningTable[note.pitch] ?: error("")) * 2.0.pow(note.octave), duration(note.duration, parameters), parameters)

   private fun duration(duration: Fraction, parameters: CompilationParameters) = 60.0 * 4.0 * duration.decimal / parameters.bpm

   private fun compileRest(rest: Int, parameters: CompilationParameters) = Wave.zeros(duration(Fraction(1, rest), parameters), samplingRate)

   private fun sound(
      frequency: Double,
      duration: Double,
      parameters: CompilationParameters
   ): Wave {
      val wave = Wave.merge(*parameters.overtones.mapIndexed { overtone, ratio ->
         Wave.sine(frequency * (overtone + 1), duration, samplingRate) * ratio
      }.toTypedArray())

      return parameters.envelope.apply(wave)
   }
}