package io.smnp.ext.synth.lib.synthesizer

import io.smnp.ext.synth.lib.wave.Wave
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine


object Synthesizer {
   const val SAMPLING_RATE = 44100.0 // [Hz]
   private val format = AudioFormat(SAMPLING_RATE.toFloat(), 8, 1, true, false)
   private var line: SourceDataLine = AudioSystem.getSourceDataLine(format)

   fun synth(wave: Wave) {
      line.write(wave.bytes, 0, wave.bytes.size)
   }

   fun init() {
      line.open(format)
      line.start()
   }

   fun dispose() {
      line.drain()
      line.stop()
      line.close()
   }
}