package io.smnp.ext.midi

import io.smnp.data.entity.Note
import io.smnp.error.EvaluationException
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.type.enumeration.DataType.*
import io.smnp.type.model.Value
import javax.sound.midi.*

object MidiSequencer {
   private const val PPQ = 1000
   private val sequencer = MidiSystem.getSequencer()

   fun playChannels(channels: Map<Int, List<List<Value>>>, config: Map<String, Any>) {
      val sequence = Sequence(Sequence.PPQ, PPQ)

      channels.forEach { (channel, lines) ->
         lines.forEach { line -> playLine(line, channel, sequence) }
      }

      sequencer.sequence = sequence
      sequencer.tempoInBPM = (config.getOrDefault("bpm", 120) as Int).toFloat()


      sequencer.start()
      while(sequencer.isRunning) Thread.sleep(20)
      sequencer.stop()
   }

   fun playLines(lines: List<List<Value>>, config: Map<String, Any>) {
      val sequence = Sequence(Sequence.PPQ, PPQ)

      lines.forEachIndexed { channel, line -> playLine(line, channel, sequence) }

      sequencer.sequence = sequence
      sequencer.tempoInBPM = (config.getOrDefault("bpm", 120) as Int).toFloat()

      sequencer.start()
      while(sequencer.isRunning) Thread.sleep(20)
      sequencer.stop()
   }

   private fun playLine(line: List<Value>, channel: Int, sequence: Sequence) {
      val track = sequence.createTrack()

      line.fold(0L) { noteOnTick, item ->
         when (item.type) {
            NOTE -> {
               note(item, channel, noteOnTick, track)
            }
            INT -> noteOnTick + 4L * PPQ / (item.value!! as Int)
            STRING -> command(item, channel, noteOnTick, track)
            else -> throw ShouldNeverReachThisLineException()
         }
      }
   }

   private fun command(item: Value, channel: Int, beginTick: Long, track: Track): Long {
      val instruction = item.value!! as String
      if(instruction.isBlank()) {
         throw EvaluationException("Empty strings are not allowed here")
      }
      val commandWithArguments = instruction.split(":")
      val (command, args) = if(commandWithArguments.size == 2) commandWithArguments else listOf(commandWithArguments[0], "0,0")
      val arguments = args.split(",")
      val cmdCode = when(command) {
         "i" -> 192 + channel
         "pitch" -> 0xE0
         else -> throw EvaluationException("Unknown command '$command'")
      }
      track.add(event(cmdCode, channel, arguments.getOrNull(0)?.toInt() ?: 0, arguments.getOrNull(1)?.toInt() ?: 0, beginTick))
      return beginTick
   }

   private fun note(item: Value, channel: Int, noteOnTick: Long, track: Track): Long {
      val note = item.value!! as Note
      val noteDuration = ((if (note.dot) 1.5 else 1.0) * 4L * PPQ / note.duration).toLong()
      val noteOffTick = noteOnTick + noteDuration
      track.add(noteOn(note, channel, noteOnTick))
      track.add(noteOff(note, channel, noteOffTick))
      return noteOffTick
   }

   private fun noteOn(note: Note, channel: Int, tick: Long): MidiEvent {
      return event(144, channel, note.intPitch() + 12, 127, tick)
   }

   private fun noteOff(note: Note, channel: Int, tick: Long): MidiEvent {
      return event(128, channel, note.intPitch() + 12, 127, tick)
   }

   private fun event(command: Int, channel: Int, data1: Int, data2: Int, tick: Long): MidiEvent {
      val message = ShortMessage()
      message.setMessage(command, channel, data1, data2)
      return MidiEvent(message, tick)
   }

   fun init() {
      sequencer.open()
   }
}