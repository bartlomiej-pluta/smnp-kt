package com.bartlomiejpluta.smnp.ext.midi.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.data.entity.Note
import com.bartlomiejpluta.smnp.data.enumeration.Pitch
import com.bartlomiejpluta.smnp.error.CustomException
import com.bartlomiejpluta.smnp.ext.midi.lib.midi.Midi
import com.bartlomiejpluta.smnp.math.Fraction
import com.bartlomiejpluta.smnp.type.enumeration.DataType.*
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.optional
import com.bartlomiejpluta.smnp.type.model.Value
import com.bartlomiejpluta.smnp.util.config.ConfigMap

class MidiHelpFunction : Function("midiHelp") {
   override fun define(new: FunctionDefinitionTool) {
      new function simple(ofType(STRING)) body { _, (command) ->
         val cmd = command.value as String
         when (cmd.toLowerCase()) {
            "instruments" -> Midi.instruments.forEachIndexed { index, instrument -> println("$index: $instrument") }
            else -> throw CustomException("Unknown command '$cmd' - available commands: 'instruments', '<instrumentId>'")
         }

         Value.void()
      }

      new function simple(
         ofType(INT),
         optional(ofType(INT)),
         optional(ofType(NOTE)),
         optional(ofType(NOTE)),
         optional(ofType(INT))
      ) body { environment, args ->
         val instrument = args[0].value as Int
         val bpm = args.getOrNull(1)?.value as Int? ?: 120
         val begin = args.getOrNull(2) ?: Value.note(Note(Pitch.C, 1, Fraction(1, 4), false))
         val end = args.getOrNull(3) ?: Value.note(Note(Pitch.H, 9, Fraction(1, 4), false))
         val channel = args.getOrNull(4)?.value as Int? ?: 1

         if(channel > 16) {
            throw CustomException("MIDI standard supports max to 16 channels and that number has been exceeded")
         }

         val notes = Value.list(
            listOf(Value.map(mapOf(Value.string("instrument") to Value.int(instrument)))) + environment.invokeFunction(
               "range",
               listOf(begin, end)
            ).value as List<Value>
         ).unwrap() as List<Any>

         println(Midi.instruments[instrument])
         println("Channel: $channel")
         println("BPM: $bpm")
         println("Range: ${begin.value} - ${end.value}")

         notes.forEachIndexed { index, it ->
            if (index > 0) {
               println(it)
               Midi
                  .with(ConfigMap(mapOf(Value.string("bpm") to Value.int(bpm))))
                  .play(mapOf(channel to listOf(listOf(mapOf("instrument" to instrument), it))))
               Thread.sleep(100)
            }
         }

         Value.void()
      }
   }
}