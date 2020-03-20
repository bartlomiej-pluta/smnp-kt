package io.smnp.ext.midi.lib.compiler

import io.smnp.data.entity.Note
import javax.sound.midi.Track

class PpqSequenceCompiler : SequenceCompiler() {
   override fun compileRest(noteOnTick: Long, item: Int, ppq: Int) = noteOnTick + item

   override fun compileNote(
      item: Note,
      channel: Int,
      noteOnTick: Long,
      track: Track,
      ppq: Int
   ): Long {
      val noteOffTick = noteOnTick + item.duration.denominator
      track.add(noteOn(item, channel, noteOnTick))
      track.add(noteOff(item, channel, noteOffTick))
      return noteOffTick
   }
}