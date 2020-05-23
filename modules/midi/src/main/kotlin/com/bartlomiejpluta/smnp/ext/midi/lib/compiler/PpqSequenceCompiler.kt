package com.bartlomiejpluta.smnp.ext.midi.lib.compiler

import com.bartlomiejpluta.smnp.data.entity.Note
import javax.sound.midi.Track

class PpqSequenceCompiler : SequenceCompiler() {
   override fun compileRest(noteOnTick: Long, item: Int, ppq: Int) = noteOnTick + item

   override fun compileNote(
      item: Note,
      velocity: Int,
      channel: Int,
      noteOnTick: Long,
      track: Track,
      ppq: Int
   ): Long {
      val noteOffTick = noteOnTick + item.duration.denominator
      track.add(noteOn(item, velocity, channel, noteOnTick))
      track.add(noteOff(item, velocity, channel, noteOffTick))
      return noteOffTick
   }
}