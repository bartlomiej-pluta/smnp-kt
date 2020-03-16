package io.smnp.ext.midi

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

   fun with(config: Map<String, Any>): SequenceExecutor {
      return SequenceExecutor(sequencer, config)
   }

   class SequenceExecutor(private val sequencer: Sequencer, private val config: Map<String, Any>) {
      fun run(lines: List<List<Any>>) {
         val sequence = Sequence(Sequence.PPQ, (config.getOrDefault("ppq", DEFAULT_PPQ) as Int))
         provideCompiler(config).compileLines(lines, sequence)
         run(sequence)
      }

      fun run(channels: Map<Int, List<List<Any>>>) {
         val sequence = Sequence(Sequence.PPQ, (config.getOrDefault("ppq", DEFAULT_PPQ) as Int))
         provideCompiler(config).compileChannels(channels, sequence)
         run(sequence)
      }

      private fun run(sequence: Sequence) {
         sequencer.sequence = sequence
         sequencer.tempoInBPM = (config.getOrDefault("bpm", DEFAULT_BPM) as Int).toFloat()

         Midi.sequencer.start()
         while (Midi.sequencer.isRunning) Thread.sleep(20)
         Midi.sequencer.stop()
      }

      fun provideCompiler(config: Map<String, Any>): SequenceCompiler =
         if(config.containsKey("ppq")) PpqSequenceCompiler()
         else DefaultSequenceCompiler()
   }

   fun init() {
      sequencer.open()
   }

   fun dispose() {
      sequencer.close()
   }
}