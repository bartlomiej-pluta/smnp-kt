package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.callable.signature.Signature.Companion.vararg
import io.smnp.error.EvaluationException
import io.smnp.ext.midi.MidiSequencer
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
         MidiSequencer.playLines(lines.unwrap() as List<List<Any>>, unwrapConfig(config))
         Value.void()
      }

      new function simple(
         mapOfMatchers(anyType(), anyType()),
         mapOfMatchers(ofType(INT), listOfMatchers(listOf(NOTE, INT, STRING)))
      ) body { _, (config, channels) ->
         MidiSequencer.playChannels(channels.unwrap() as Map<Int, List<List<Any>>>, unwrapConfig(config))
         Value.void()
      }
   }

   private fun unwrapConfig(config: Value): Map<String, Any> {
      return (config.unwrap() as Map<String, Any>)
         .map { (key, value) ->
            key to when(key) {
               "bpm" -> value as? Int ?: throw EvaluationException("Invalid parameter type: 'bpm' is supposed to be of int type")
               else -> value
            }
         }.toMap()
   }
}