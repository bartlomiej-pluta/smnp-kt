package io.smnp.ext.midi.lib.compiler

import io.smnp.data.entity.Note
import javax.sound.midi.Track

class DefaultSequenceCompiler : SequenceCompiler() {
   override fun compileRest(noteOnTick: Long, item: Int, ppq: Int) = noteOnTick + 4L * ppq / item

   override fun compileNote(
      item: Note,
      channel: Int,
      noteOnTick: Long,
      track: Track,
      ppq: Int
   ): Long {
      val noteDuration = (4L * ppq * item.duration.decimal).toLong()
      val noteOffTick = noteOnTick + noteDuration
      track.add(noteOn(item, channel, noteOnTick))
      track.add(noteOff(item, channel, noteOffTick))
      return noteOffTick
   }
}