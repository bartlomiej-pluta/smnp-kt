package io.smnp.dsl.token.tokenizer

import io.smnp.dsl.token.model.entity.Token
import io.smnp.dsl.token.model.entity.TokenizerOutput
import io.smnp.dsl.token.model.enumeration.TokenType

class FloatTokenizer : Tokenizer {
    override fun tokenize(input: String, current: Int, line: Int, source: String): TokenizerOutput {
        return Tokenizer.combined(
            { values, tokenPosition -> Token(TokenType.FLOAT, values.joinToString(""), tokenPosition) },
            Tokenizer.regex(TokenType.NONE, "\\d"),
            Tokenizer.keyword(TokenType.NONE, "."),
            Tokenizer.regex(TokenType.NONE, "\\d")
        ).tokenize(input, current, line, source)
    }
}