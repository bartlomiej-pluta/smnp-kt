package com.bartlomiejpluta.smnp.ext.midi.function

import com.bartlomiejpluta.smnp.callable.function.Function
import com.bartlomiejpluta.smnp.callable.function.FunctionDefinitionTool
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.simple
import com.bartlomiejpluta.smnp.callable.signature.Signature.Companion.vararg
import com.bartlomiejpluta.smnp.error.CustomException
import com.bartlomiejpluta.smnp.ext.midi.lib.midi.Midi
import com.bartlomiejpluta.smnp.type.enumeration.DataType.*
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.anyType
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.listOf
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.listOfMatchers
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.mapOfMatchers
import com.bartlomiejpluta.smnp.type.matcher.Matcher.Companion.ofType
import com.bartlomiejpluta.smnp.type.model.Value
import com.bartlomiejpluta.smnp.util.config.ConfigMapSchema


class MidiFunction : Function("midi") {
   private val schema = ConfigMapSchema()
      .optional("bpm", ofType(INT), Value.int(120))
      .optional("ppq", ofType(INT))
      .optional("output", ofType(STRING))
      .optional("play", ofType(BOOL))
      .optional("velocity", ofType(FLOAT), Value.float(1.0F))
      .optional("instrument", ofType(INT), Value.int(0))

   override fun define(new: FunctionDefinitionTool) {
      new function vararg(
         listOfMatchers(ofType(NOTE), ofType(INT), mapOfMatchers(ofType(STRING), anyType())),
         mapOfMatchers(ofType(STRING), anyType())
      ) body { _, (config, lines) ->
         val unwrappedLines = lines.unwrap() as List<List<Any>>

         if (unwrappedLines.size > 16) {
            throw CustomException("MIDI standard supports max to 16 channels and that number has been exceeded")
         }

         Midi.with(schema.parse(config)).play(unwrappedLines)

         Value.void()
      }

      new function vararg(listOfMatchers(ofType(NOTE), ofType(INT), mapOfMatchers(ofType(STRING), anyType()))) body { _, (lines) ->
         val unwrappedLines = lines.unwrap() as List<List<Any>>

         if (unwrappedLines.size > 16) {
            throw CustomException("MIDI standard supports max to 16 channels and that number has been exceeded")
         }

         Midi.with(schema.empty()).play(unwrappedLines)

         Value.void()
      }

      new function simple(
         mapOfMatchers(ofType(STRING), anyType()),
         mapOfMatchers(ofType(INT), listOfMatchers(listOf(NOTE, INT, STRING)))
      ) body { _, (config, channels) ->
         val unwrappedChannels = channels.unwrap() as Map<Int, List<List<Any>>>

         if (unwrappedChannels.size > 16 || unwrappedChannels.any { (k) -> k > 16 }) {
            throw CustomException("MIDI standard supports max to 16 channels and that number has been exceeded")
         }

         Midi.with(schema.parse(config)).play(unwrappedChannels)

         Value.void()
      }

      new function simple(mapOfMatchers(ofType(INT), listOfMatchers(listOf(NOTE, INT, STRING)))) body { _, (channels) ->
         val unwrappedChannels = channels.unwrap() as Map<Int, List<List<Any>>>

         if (unwrappedChannels.size > 16 || unwrappedChannels.any { (k) -> k > 16 }) {
            throw CustomException("MIDI standard supports max to 16 channels and that number has been exceeded")
         }

         Midi.with(schema.empty()).play(unwrappedChannels)

         Value.void()
      }

      new function simple(ofType(STRING)) body { _, (file) ->
         Midi.playFile(file.value as String)
         Value.void()
      }
   }
}