package com.bartlomiejpluta.smnp.ext.lang.constructor

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.data.entity.Note
import com.bartlomiejpluta.smnp.data.enumeration.Pitch
import com.bartlomiejpluta.smnp.math.Fraction
import com.bartlomiejpluta.smnp.type.enumeration.DataType.*
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.model.Value

class NoteConstructor : Function("Note") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(
         ofType(STRING),
         ofType(INT),
         ofType(INT),
         ofType(BOOL)
      ) body { _, (pitchString, octave, duration, dot) ->
         val pitch = Pitch.parse((pitchString.value as String).toLowerCase())
         val note = Note(pitch, octave.value as Int, Fraction(1, duration.value as Int), dot.value as Boolean)
         Value.note(note)
      }

      new function simple(
         ofType(STRING),
         ofType(INT),
         ofType(INT),
         ofType(INT)
      ) body { _, (pitchString, octave, durationNumerator, durationDenominator) ->
         val pitch = Pitch.parse((pitchString.value as String).toLowerCase())
         val note = Note(
            pitch,
            octave.value as Int,
            Fraction(durationNumerator.value as Int, durationDenominator.value as Int),
            false
         )
         Value.note(note)
      }
   }
}