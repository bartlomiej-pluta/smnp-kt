package io.smnp.ext.synth.lib.model

import io.smnp.data.enumeration.Pitch
import io.smnp.ext.synth.lib.envelope.EnvelopeFactory
import io.smnp.util.config.ConfigMap
import kotlin.math.pow

private val SEMITONE = 2.0.pow(1.0 / 12.0)

class CompilationParameters(config: ConfigMap) {
   val velocity by lazy { (config["velocity"].value as Float).coerceIn(0.0F, 1.0F) }
   val envelope by lazy { EnvelopeFactory.provideEnvelope(config["envelope"]) }
   val overtones by lazy { (config["overtones"].unwrap() as List<Float>).map { it.toDouble() } }
   val bpm by lazy { config["bpm"].value as Int }
   val tuningTable by lazy {
      Pitch.values()
         .mapIndexed { index, pitch -> pitch to (config["tuning"].value as Float).toDouble() / SEMITONE.pow(57 - index) }
         .toMap()
   }
}