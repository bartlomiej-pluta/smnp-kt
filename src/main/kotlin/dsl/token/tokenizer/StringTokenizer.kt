package dsl.token.tokenizer

import dsl.token.model.entity.TokenizerOutput
import dsl.token.model.enumeration.TokenType

class StringTokenizer : Tokenizer {
    override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
        if(input[current] == '"') {
            var value = input[current].toString()
            var consumedChars = 1
            while(input.length > current + consumedChars && input[current+consumedChars] != '"') {
                value += input[current + consumedChars]
                consumedChars += 1
            }

            value += input[current + consumedChars]
            consumedChars += 1

            return TokenizerOutput.produce(consumedChars, value.substring(1, value.length-1), value, TokenType.STRING, line, current)
        }

        return TokenizerOutput.NONE
    }
}