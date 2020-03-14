package io.smnp.ext.lang.constructor

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.data.entity.Note
import io.smnp.data.enumeration.Pitch
import io.smnp.type.enumeration.DataType.*
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value

class NoteConstructor : Function("Note") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(
         ofType(STRING),
         ofType(INT),
         ofType(INT),
         ofType(BOOL)
      ) body { _, (pitchString, octave, duration, dot) ->
         val pitch = Pitch.parse((pitchString.value as String).toLowerCase())
         val note = Note(pitch, octave.value as Int, duration.value as Int, dot.value as Boolean)
         Value.note(note)
      }
   }
}