package io.smnp.data.entity

import io.smnp.data.enumeration.Pitch
import io.smnp.math.Fraction

class Note(val pitch: Pitch, val octave: Int, duration: Fraction, dot: Boolean) {
   val duration = if(dot) duration * Fraction(3, 2) else duration

   operator fun plus(duration2: Fraction) = Note(pitch, octave, duration + duration2, false)

   override fun toString(): String {
      return "${pitch}${octave}:(${duration})"
   }

   fun intPitch(): Int {
      return octave * 12 + pitch.ordinal
   }

   override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as Note

      if (pitch != other.pitch) return false
      if (octave != other.octave) return false
      if (duration != other.duration) return false

      return true
   }

   override fun hashCode(): Int {
      var result = pitch.hashCode()
      result = 31 * result + octave
      result = 31 * result + duration.hashCode()
      return result
   }
}