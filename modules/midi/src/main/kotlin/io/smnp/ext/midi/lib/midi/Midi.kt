package io.smnp.ext.midi.lib.midi

import io.smnp.ext.midi.lib.compiler.DefaultSequenceCompiler
import io.smnp.ext.midi.lib.compiler.PpqSequenceCompiler
import io.smnp.ext.midi.lib.compiler.SequenceCompiler
import io.smnp.util.config.ConfigMap
import java.io.File
import javax.sound.midi.MidiSystem
import javax.sound.midi.Sequence
import javax.sound.midi.Sequencer

object Midi {
   private const val DEFAULT_PPQ = 1000
   private const val MIDI_FILE_TYPE = 1
   private val sequencer = MidiSystem.getSequencer()
   private val synthesizer = MidiSystem.getSynthesizer()

   val instruments: List<String>
      get() = synthesizer.availableInstruments.map { it.toString() }

   fun playFile(file: String) {
      playSequence(MidiSystem.getSequence(File(file)))
   }

   private fun playSequence(sequence: Sequence, prepare: (Sequencer) -> Unit = {}) {
      prepare(sequencer)
      sequencer.sequence = sequence

      sequencer.start()
      while (sequencer.isRunning) Thread.sleep(20)
      sequencer.stop()
   }

   fun with(config: ConfigMap): SequenceExecutor {
      return SequenceExecutor(
         sequencer,
         config
      )
   }

   class SequenceExecutor(private val sequencer: Sequencer, private val config: ConfigMap) {
      fun play(lines: List<List<Any>>) {
         val sequence = Sequence(Sequence.PPQ, config.getUnwrappedOrDefault("ppq",
            DEFAULT_PPQ
         ))
         provideCompiler(config).compileLines(lines, sequence)
         play(sequence)
         writeToFile(sequence)
      }

      private fun provideCompiler(config: ConfigMap): SequenceCompiler =
         if (config.containsKey("ppq")) PpqSequenceCompiler()
         else DefaultSequenceCompiler()

      private fun play(sequence: Sequence) {
         config.getUnwrappedOrDefault("play", true).takeIf { it }?.let {
            playSequence(sequence) {
               sequencer.tempoInBPM = (config["bpm"].value as Int).toFloat()
            }
         }
      }

      private fun writeToFile(sequence: Sequence) {
         config.getUnwrappedOrDefault<String?>("output", null)?.let {
            MidiSystem.write(sequence, MIDI_FILE_TYPE, File(it))
         }
      }

      fun play(channels: Map<Int, List<List<Any>>>) {
         val sequence = Sequence(Sequence.PPQ, config.getUnwrappedOrDefault("ppq",
            DEFAULT_PPQ
         ))
         provideCompiler(config).compileChannels(channels, sequence)
         play(sequence)
         writeToFile(sequence)
      }
   }

   fun init() {
      sequencer.open()
   }

   fun dispose() {
      sequencer.close()
   }
}