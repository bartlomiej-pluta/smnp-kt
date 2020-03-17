package io.smnp.ext.midi

import java.io.File
import javax.sound.midi.MidiSystem
import javax.sound.midi.Sequence
import javax.sound.midi.Sequencer

object Midi {
   private const val DEFAULT_PPQ = 1000
   private const val DEFAULT_BPM = 120
   private val sequencer = MidiSystem.getSequencer()
   private val synthesizer = MidiSystem.getSynthesizer()

   val instruments: List<String>
   get() = synthesizer.availableInstruments.map { it.toString() }

   fun playFile(file: String) {
      playSequence(MidiSystem.getSequence(File(file)))
   }

   private fun playSequence(sequence: Sequence, prepare: (Sequencer) -> Unit = {}) {
      sequencer.sequence = sequence

      sequencer.start()
      while (sequencer.isRunning) Thread.sleep(20)
      sequencer.stop()
   }

   fun with(config: Map<String, Any>): SequenceExecutor {
      return SequenceExecutor(sequencer, config)
   }

   class SequenceExecutor(private val sequencer: Sequencer, private val config: Map<String, Any>) {
      fun play(lines: List<List<Any>>) {
         val sequence = Sequence(Sequence.PPQ, (config.getOrDefault("ppq", DEFAULT_PPQ) as Int))
         provideCompiler(config).compileLines(lines, sequence)
         play(sequence)
      }

      private fun provideCompiler(config: Map<String, Any>): SequenceCompiler =
         if(config.containsKey("ppq")) PpqSequenceCompiler()
         else DefaultSequenceCompiler()

      private fun play(sequence: Sequence) {
         playSequence(sequence) {
            Midi.sequencer.tempoInBPM = (config.getOrDefault("bpm", DEFAULT_BPM) as Int).toFloat()
         }
      }

      fun play(channels: Map<Int, List<List<Any>>>) {
         val sequence = Sequence(Sequence.PPQ, (config.getOrDefault("ppq", DEFAULT_PPQ) as Int))
         provideCompiler(config).compileChannels(channels, sequence)
         play(sequence)
      }
   }

   fun init() {
      sequencer.open()
   }

   fun dispose() {
      sequencer.close()
   }
}