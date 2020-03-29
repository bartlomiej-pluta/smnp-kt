package io.smnp.dsl.token.tokenizer

import io.smnp.dsl.token.model.entity.TokenizerOutput
import io.smnp.dsl.token.model.enumeration.TokenType

class StringTokenizer : Tokenizer {
    override fun tokenize(input: String, current: Int, line: Int, source: String): TokenizerOutput {
        if(input[current] == '"') {
            var value = input[current].toString()
            var consumedChars = 1
            while(input.length > current + consumedChars && input[current+consumedChars] != '"') {
                value += input[current + consumedChars]
                consumedChars += 1
            }

            value += input[current + consumedChars]
            consumedChars += 1

            return TokenizerOutput.produce(consumedChars, value.substring(1, value.length-1), value, TokenType.STRING, source, line, current)
        }

        return TokenizerOutput.NONE
    }
}