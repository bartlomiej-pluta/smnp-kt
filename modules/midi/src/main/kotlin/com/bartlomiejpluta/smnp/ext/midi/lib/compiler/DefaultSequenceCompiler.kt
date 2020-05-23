package com.bartlomiejpluta.smnp.ext.midi.lib.compiler

import com.bartlomiejpluta.smnp.data.entity.Note
import javax.sound.midi.Track

class DefaultSequenceCompiler : SequenceCompiler() {
   override fun compileRest(noteOnTick: Long, item: Int, ppq: Int) = noteOnTick + 4L * ppq / item

   override fun compileNote(
      item: Note,
      velocity: Int,
      channel: Int,
      noteOnTick: Long,
      track: Track,
      ppq: Int
   ): Long {
      val noteDuration = (4L * ppq * item.duration.decimal).toLong()
      val noteOffTick = noteOnTick + noteDuration
      track.add(noteOn(item, velocity, channel, noteOnTick))
      track.add(noteOff(item, velocity, channel, noteOffTick))
      return noteOffTick
   }
}