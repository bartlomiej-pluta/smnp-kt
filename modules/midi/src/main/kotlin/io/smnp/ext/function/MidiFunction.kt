package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.callable.signature.Signature.Companion.vararg
import io.smnp.error.CustomException
import io.smnp.ext.midi.Midi
import io.smnp.type.enumeration.DataType.*
import io.smnp.type.matcher.Matcher.Companion.anyType
import io.smnp.type.matcher.Matcher.Companion.listOf
import io.smnp.type.matcher.Matcher.Companion.listOfMatchers
import io.smnp.type.matcher.Matcher.Companion.mapOfMatchers
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value


class MidiFunction : Function("midi") {
   override fun define(new: FunctionDefinitionTool) {
      new function vararg(
         listOf(NOTE, INT, STRING),
         mapOfMatchers(ofType(STRING), anyType())
      ) body { _, (config, lines) ->
         val unwrappedLines = lines.unwrap() as List<List<Any>>

         if (unwrappedLines.size > 16) {
            throw CustomException("MIDI standard supports max to 16 channels and that number has been exceeded")
         }

         Midi.with(unwrapConfig(config)).play(unwrappedLines)

         Value.void()
      }

      new function vararg(listOf(NOTE, INT, STRING)) body { _, (lines) ->
         val unwrappedLines = lines.unwrap() as List<List<Any>>

         if (unwrappedLines.size > 16) {
            throw CustomException("MIDI standard supports max to 16 channels and that number has been exceeded")
         }

         Midi.with(emptyMap()).play(unwrappedLines)

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

         Midi.with(unwrapConfig(config)).play(unwrappedChannels)

         Value.void()
      }

      new function simple(mapOfMatchers(ofType(INT), listOfMatchers(listOf(NOTE, INT, STRING)))) body { _, (channels) ->
         val unwrappedChannels = channels.unwrap() as Map<Int, List<List<Any>>>

         if (unwrappedChannels.size > 16 || unwrappedChannels.any { (k) -> k > 16 }) {
            throw CustomException("MIDI standard supports max to 16 channels and that number has been exceeded")
         }

         Midi.with(emptyMap()).play(unwrappedChannels)

         Value.void()
      }

      new function simple(ofType(STRING)) body { _, (file) ->
         Midi.playFile(file.value as String)
         Value.void()
      }
   }

   private fun unwrapConfig(config: Value): Map<String, Any> {
      return (config.unwrap() as Map<String, Any>)
         .map { (key, value) ->
            key to when (key) {
               "bpm" -> value as? Int
                  ?: throw CustomException("Invalid parameter type: 'bpm' is supposed to be of int type")
               "ppq" -> value as? Int
                  ?: throw CustomException("Invalid parameter type: 'ppq' is supposed to be of int type")
               "output" -> value as? String
                  ?: throw CustomException("Invalid parameter type: 'output' is supposed to be of string type")
               "play" -> value as? Boolean
                  ?: throw CustomException("Invalid parameter type: 'play' is supposed to be of bool type")
               else -> value
            }
         }.toMap()
   }
}