package io.smnp.data.entity

import io.smnp.data.enumeration.Pitch

data class Note(val pitch: Pitch, val octave: Int, val duration: Int, val dot: Boolean) {
   data class Builder(
      var pitch: Pitch = Pitch.A,
      var octave: Int = 4,
      var duration: Int = 4,
      var dot: Boolean = false
   ) {
      fun pitch(pitch: Pitch) = apply { this.pitch = pitch }
      fun octave(octave: Int) = apply { this.octave = octave }
      fun duration(duration: Int) = apply { this.duration = duration }
      fun dot(dot: Boolean) = apply { this.dot = dot }
      fun build() = Note(pitch, octave, duration, dot)
   }

   override fun toString(): String {
      return "${pitch}${octave}:${duration}${if (dot) "d" else ""}"
   }

   fun intPitch(): Int {
      return octave * 12 + pitch.ordinal
   }
}