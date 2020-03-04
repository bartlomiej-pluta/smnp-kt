package dsl.token.tokenizer

import dsl.token.model.entity.TokenizerOutput
import dsl.token.model.enumeration.TokenType

class CommentTokenizer : Tokenizer {
    override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
        if (input[current] == '#') {
            var consumedChars = 0
            var value = ""
            while(current + consumedChars < input.length) {
                value += input[current + consumedChars]
                consumedChars += 1
            }

            return TokenizerOutput.produce(consumedChars, value.substring(1).trim(), value, TokenType.COMMENT, line, current)
        }

        return TokenizerOutput.NONE
    }
}