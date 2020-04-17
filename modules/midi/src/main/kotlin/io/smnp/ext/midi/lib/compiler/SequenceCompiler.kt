package io.smnp.ext.midi.lib.compiler

import io.smnp.data.entity.Note
import io.smnp.error.CustomException
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.util.config.ConfigMap
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

   fun compileChannels(channels: Map<Int, List<List<Any>>>, config: ConfigMap, sequence: Sequence) {
      channels.forEach { (channel, lines) ->
         lines.forEach { line -> compileLine(line, channel - 1, config, sequence) }
      }
   }

   fun compileLines(lines: List<List<Any>>, config: ConfigMap, sequence: Sequence) {
      lines.forEachIndexed { channel, line -> compileLine(line, channel, config, sequence) }
   }

   protected abstract fun compileNote(
      item: Note,
      velocity: Int,
      channel: Int,
      noteOnTick: Long,
      track: Track,
      ppq: Int
   ): Long

   abstract fun compileRest(noteOnTick: Long, item: Int, ppq: Int): Long

   private fun compileLine(line: List<Any>, channel: Int, config: ConfigMap, sequence: Sequence) {
      val track = sequence.createTrack()

      track.add(event(Command.PROGRAM_CHANGE + channel, channel, config["instrument"].value as Int, 0, 0))
      var velocity = velocityToInt(config["velocity"].value as Float)

      val lastTick = line.fold(0L) { noteOnTick, item ->
         when (item) {
            is Note -> compileNote(item, velocity, channel, noteOnTick, track, sequence.resolution)
            is Int -> compileRest(noteOnTick, item, sequence.resolution)
            is Map<*, *> -> {
               val command = item as Map<String, Any>
               command["instrument"]?.let {
                  val value = it as? Int ?: throw CustomException("Invalid parameter type: 'instrument' is supposed to be of int type")
                  track.add(event(Command.PROGRAM_CHANGE + channel, channel, value, 0, noteOnTick))
               }

               command["velocity"]?.let {
                  val value = it as? Float ?: throw CustomException("Invalid parameter type: 'velocity' is supposed to be of float type")
                  velocity = velocityToInt(value)
               }

               noteOnTick
            }
            else -> throw ShouldNeverReachThisLineException()
         }
      }

      track.add(allNotesOff(channel, lastTick))
   }

   private fun velocityToInt(value: Float) = (127.0 * value.coerceIn(0.0F, 1.0F)).toInt()

   protected fun noteOn(note: Note, velocity: Int, channel: Int, tick: Long): MidiEvent {
      return event(Command.NOTE_ON, channel, note.intPitch() + 12, velocity, tick)
   }

   protected fun noteOff(note: Note, velocity: Int, channel: Int, tick: Long): MidiEvent {
      return event(Command.NOTE_OFF, channel, note.intPitch() + 12, velocity, tick)
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