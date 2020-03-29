package io.smnp.dsl.token.tokenizer

import io.smnp.dsl.token.model.entity.TokenizerOutput
import io.smnp.dsl.token.model.enumeration.TokenType

class CommentTokenizer : Tokenizer {
    override fun tokenize(input: String, current: Int, line: Int, source: String): TokenizerOutput {
        if (input[current] == '#') {
            var consumedChars = 0
            var value = ""
            while(current + consumedChars < input.length) {
                value += input[current + consumedChars]
                consumedChars += 1
            }

            return TokenizerOutput.produce(consumedChars, value.substring(1).trim(), value, TokenType.COMMENT, source, line, current)
        }

        return TokenizerOutput.NONE
    }
}