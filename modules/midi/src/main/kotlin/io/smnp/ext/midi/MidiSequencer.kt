package io.smnp.ext.midi

import io.smnp.data.entity.Note
import io.smnp.error.EvaluationException
import io.smnp.error.ShouldNeverReachThisLineException
import javax.sound.midi.*

object MidiSequencer {
   private const val PPQ = 1000
   private const val DEFAULT_BPM = 120
   private val sequencer = MidiSystem.getSequencer()

   private object Command {
      const val NOTE_OFF = 0x80
      const val NOTE_ON = 0x90
      const val PROGRAM_CHANGE = 0xC0
      const val CONTROL_CHANGE = 0xB0
   }

   private object Data1 {
      const val ALL_NOTES_OFF = 0x7B
   }

   private object Data2 {
      const val ZERO = 0x00
   }

   fun playChannels(channels: Map<Int, List<List<Any>>>, config: Map<String, Any>) {
      val sequence = Sequence(Sequence.PPQ, PPQ)

      channels.forEach { (channel, lines) ->
         lines.forEach { line -> playLine(line, channel-1, sequence) }
      }

      sequencer.sequence = sequence
      sequencer.tempoInBPM = (config.getOrDefault("bpm", DEFAULT_BPM) as Int).toFloat()


      sequencer.start()
      while(sequencer.isRunning) Thread.sleep(20)
      sequencer.stop()
   }

   fun playLines(lines: List<List<Any>>, config: Map<String, Any>) {
      val sequence = Sequence(Sequence.PPQ, PPQ)

      lines.forEachIndexed { channel, line -> playLine(line, channel, sequence) }

      sequencer.sequence = sequence
      sequencer.tempoInBPM = (config.getOrDefault("bpm", DEFAULT_BPM) as Int).toFloat()

      sequencer.start()
      while(sequencer.isRunning) Thread.sleep(20)
      sequencer.stop()
   }

   private fun playLine(line: List<Any>, channel: Int, sequence: Sequence) {
      val track = sequence.createTrack()

      val lastTick = line.fold(0L) { noteOnTick, item ->
         when (item) {
            is Note -> {
               note(item, channel, noteOnTick, track)
            }
            is Int -> noteOnTick + 4L * PPQ / item
            is String -> command(item, channel, noteOnTick, track)
            else -> throw ShouldNeverReachThisLineException()
         }
      }

      track.add(allNotesOff(channel, lastTick))
   }

   private fun command(instruction: String, channel: Int, beginTick: Long, track: Track): Long {
      if(instruction.isBlank()) {
         throw EvaluationException("Empty strings are not allowed here")
      }
      val commandWithArguments = instruction.split(":")
      val (command, args) = if(commandWithArguments.size == 2) commandWithArguments else listOf(commandWithArguments[0], "0,0")
      val arguments = args.split(",")
      val cmdCode = when(command) {
         "i" -> Command.PROGRAM_CHANGE + channel
         else -> throw EvaluationException("Unknown command '$command'")
      }
      track.add(event(cmdCode, channel, arguments.getOrNull(0)?.toInt() ?: 0, arguments.getOrNull(1)?.toInt() ?: 0, beginTick))
      return beginTick
   }

   private fun note(note: Note, channel: Int, noteOnTick: Long, track: Track): Long {
      val noteDuration = ((if (note.dot) 1.5 else 1.0) * 4L * PPQ / note.duration).toLong()
      val noteOffTick = noteOnTick + noteDuration
      track.add(noteOn(note, channel, noteOnTick))
      track.add(noteOff(note, channel, noteOffTick))
      return noteOffTick
   }

   private fun noteOn(note: Note, channel: Int, tick: Long): MidiEvent {
      return event(Command.NOTE_ON, channel, note.intPitch() + 12, 127, tick)
   }

   private fun noteOff(note: Note, channel: Int, tick: Long): MidiEvent {
      return event(Command.NOTE_OFF, channel, note.intPitch() + 12, 127, tick)
   }

   private fun allNotesOff(channel: Int, tick: Long): MidiEvent {
      return event(Command.CONTROL_CHANGE, channel, Data1.ALL_NOTES_OFF, Data2.ZERO, tick)
   }

   private fun event(command: Int, channel: Int, data1: Int, data2: Int, tick: Long): MidiEvent {
      val message = ShortMessage(command, channel, data1, data2)
      return MidiEvent(message, tick)
   }

   fun init() {
      sequencer.open()
   }

   fun dispose() {
      sequencer.close()
   }
}