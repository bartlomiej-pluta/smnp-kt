package io.smnp.ext.midi.lib.compiler

import io.smnp.data.entity.Note
import io.smnp.error.EvaluationException
import io.smnp.error.ShouldNeverReachThisLineException
import javax.sound.midi.MidiEvent
import javax.sound.midi.Sequence
import javax.sound.midi.ShortMessage
import javax.sound.midi.Track

abstract class SequenceCompiler {
   protected object Command {
      const val NOTE_OFF = 0x80
      const val NOTE_ON = 0x90
      const val PROGRAM_CHANGE = 0xC0
      const val CONTROL_CHANGE = 0xB0
   }

   protected object Data1 {
      const val ALL_NOTES_OFF = 0x7B
   }

   protected object Data2 {
      const val ZERO = 0x00
   }

   fun compileLines(lines: List<List<Any>>, sequence: Sequence) =
      lines.forEachIndexed { channel, line -> compileLine(line, channel, sequence) }

   fun compileChannels(channels: Map<Int, List<List<Any>>>, sequence: Sequence) = channels.forEach { (channel, lines) ->
      lines.forEach { line -> compileLine(line, channel - 1, sequence) }
   }

   protected abstract fun compileNote(
      item: Note,
      channel: Int,
      noteOnTick: Long,
      track: Track,
      ppq: Int
   ): Long

   abstract fun compileRest(noteOnTick: Long, item: Int, ppq: Int): Long

   private fun compileLine(line: List<Any>, channel: Int, sequence: Sequence) {
      val track = sequence.createTrack()

      val lastTick = line.fold(0L) { noteOnTick, item ->
         when (item) {
            is Note -> compileNote(item, channel, noteOnTick, track, sequence.resolution)
            is Int -> compileRest(noteOnTick, item, sequence.resolution)
            is String -> command(item, channel, noteOnTick, track)
            else -> throw ShouldNeverReachThisLineException()
         }
      }

      track.add(allNotesOff(channel, lastTick))
   }

   private fun command(instruction: String, channel: Int, beginTick: Long, track: Track): Long {
      if (instruction.isBlank()) {
         throw EvaluationException("Empty strings are not allowed here")
      }
      val commandWithArguments = instruction.split(":")
      val (command, args) = if (commandWithArguments.size == 2) commandWithArguments else listOf(
         commandWithArguments[0],
         "0,0"
      )
      val arguments = args.split(",")
      val cmdCode = when (command) {
         "i" -> Command.PROGRAM_CHANGE + channel
         else -> throw EvaluationException("Unknown command '$command'")
      }
      track.add(
         event(
            cmdCode,
            channel,
            arguments.getOrNull(0)?.toInt() ?: 0,
            arguments.getOrNull(1)?.toInt() ?: 0,
            beginTick
         )
      )
      return beginTick
   }

   protected fun noteOn(note: Note, channel: Int, tick: Long): MidiEvent {
      return event(Command.NOTE_ON, channel, note.intPitch() + 12, 127, tick)
   }

   protected fun noteOff(note: Note, channel: Int, tick: Long): MidiEvent {
      return event(Command.NOTE_OFF, channel, note.intPitch() + 12, 127, tick)
   }

   private fun allNotesOff(channel: Int, tick: Long): MidiEvent {
      return event(
         Command.CONTROL_CHANGE, channel,
         Data1.ALL_NOTES_OFF,
         Data2.ZERO, tick)
   }

   private fun event(command: Int, channel: Int, data1: Int, data2: Int, tick: Long): MidiEvent {
      val message = ShortMessage(command, channel, data1, data2)
      return MidiEvent(message, tick)
   }
}