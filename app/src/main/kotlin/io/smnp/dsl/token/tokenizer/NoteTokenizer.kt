package io.smnp.dsl.token.tokenizer

import io.smnp.data.entity.Note
import io.smnp.data.enumeration.Pitch
import io.smnp.dsl.token.model.entity.TokenizerOutput
import io.smnp.dsl.token.model.enumeration.TokenType
import io.smnp.math.Fraction

class NoteTokenizer : Tokenizer {
   override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
      var consumedChars = 0
      var notePitch: String
      var octave: Int? = null
      var duration: String? = null
      var dot = false
      var rawValue = ""

      // Note literal start symbol
      if (input[current] == '@') {
         rawValue += input[current + consumedChars]
         consumedChars += 1

         // Note basic pitch
         if (listOf('c', 'd', 'e', 'f', 'g', 'a', 'h', 'b').contains(input[current + consumedChars].toLowerCase())) {
            rawValue += input[current + consumedChars]
            notePitch = input[current + consumedChars].toString()
            consumedChars += 1

            // Flat or sharp
            if (current + consumedChars < input.length && listOf('b', '#').contains(input[current + consumedChars])) {
               rawValue += input[current + consumedChars]
               notePitch += input[current + consumedChars]
               consumedChars += 1
            }

            // Octave
            if (current + consumedChars < input.length && "\\d".toRegex().matches(input[current + consumedChars].toString())) {
               rawValue += input[current + consumedChars]
               octave = input[current + consumedChars].toString().toInt()
               consumedChars += 1
            }

            // Duration start symbol
            if (current + consumedChars < input.length && input[current + consumedChars] == ':') {
               rawValue += input[current + consumedChars]
               duration = ""
               consumedChars += 1
               while (current + consumedChars < input.length && "\\d".toRegex().matches(input[current + consumedChars].toString())) {
                  rawValue += input[current + consumedChars]
                  duration += input[current + consumedChars]
                  consumedChars += 1
               }

               if (duration.isEmpty()) {
                  return TokenizerOutput.NONE
               }
               dot = (current + consumedChars < input.length && input[current + consumedChars] == 'd')

               if (dot) {
                  rawValue += input[current + consumedChars]
                  consumedChars += 1
               }
            }

            val note = Note(Pitch.parse(notePitch), octave ?: 4, Fraction(1, duration?.toInt() ?: 4), dot)

            return TokenizerOutput.produce(consumedChars, note, rawValue, TokenType.NOTE, line, current)
         }
      }

      return TokenizerOutput.NONE
   }
}