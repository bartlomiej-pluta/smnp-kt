package io.smnp.ext.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.simple
import io.smnp.callable.signature.Signature.Companion.vararg
import io.smnp.error.EvaluationException
import io.smnp.ext.midi.MidiSequencer
import io.smnp.type.enumeration.DataType.*
import io.smnp.type.matcher.Matcher.Companion.allTypes
import io.smnp.type.matcher.Matcher.Companion.listOf
import io.smnp.type.matcher.Matcher.Companion.listOfMatchers
import io.smnp.type.matcher.Matcher.Companion.mapOfMatchers
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value


class MidiFunction : Function("midi") {
   override fun define(new: FunctionDefinitionTool) {
      new function vararg(
         listOf(NOTE, INT, STRING),
         mapOfMatchers(ofType(INT), allTypes())
      ) body { _, (config, lines) ->

         val lines = (lines.value!! as List<Value>).map { it.value!! as List<Value> }
         val parameters = configParametersMap(config.value!!)
         MidiSequencer.playLines(lines, parameters)
         Value.void()
      }

      new function simple(
         mapOfMatchers(allTypes(), allTypes()),
         mapOfMatchers(ofType(INT), listOfMatchers(listOf(NOTE, INT, STRING)))
      ) body { _, (config, channels) ->
         val channels = (channels.value!! as Map<Value, Value>).map { (key, value) ->
            key.value!! as Int to ((value.value!! as List<Value>).map { it.value!! as List<Value> })
         }.toMap()

         val parameters = configParametersMap(config.value!!)
         MidiSequencer.playChannels(channels, parameters)

         Value.void()
      }
   }

   private fun configParametersMap(config: Any): Map<String, Any> {
      return (config as Map<Value, Value>)
         .map { (key, value) -> key.value!! as String to value }
         .map { (key, value) ->
            key to when (key) {
               "bpm" -> if (value.type == INT) value.value!! else throw EvaluationException("Invalid parameter type: 'bpm' is supposed to be of int type")
               else -> value
            }
         }
         .toMap()
   }
}