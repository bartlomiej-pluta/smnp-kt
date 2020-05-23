package com.bartlomiejpluta.smnp.dsl.token.model.entity

import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

data class TokenizerOutput(val consumedChars: Int, val token: Token) {
    fun consumed(): Boolean {
        return consumedChars > 0
    }

    fun mapToken(mapper: (Token) -> Token): TokenizerOutput {
        return TokenizerOutput(consumedChars, mapper(token) )
    }

    companion object {
        val NONE = TokenizerOutput(0, Token.NONE)

        fun produce(consumedChars: Int, token: Token): TokenizerOutput {
            return if (consumedChars > 0) TokenizerOutput(consumedChars, token) else NONE
        }

        fun produce(consumedChars: Int, value: String, tokenType: TokenType, source: String, line: Int, beginCol: Int): TokenizerOutput {
            return produce(consumedChars, Token(tokenType, value, TokenPosition(source, line, beginCol, beginCol + consumedChars)))
        }

        fun produce(consumedChars: Int, value: Any, rawValue: String, tokenType: TokenType, source: String, line: Int, beginCol: Int): TokenizerOutput {
            return produce(consumedChars, Token(tokenType, value, rawValue, TokenPosition(source, line, beginCol, beginCol + consumedChars)))
        }
    }
}