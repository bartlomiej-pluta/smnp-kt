package io.smnp.dsl.token.model.entity

import io.smnp.dsl.token.model.enumeration.TokenType

data class Token(val type: TokenType, val value: Any, val rawValue: String, val position: TokenPosition) {
    constructor(type: TokenType, value: String, position: TokenPosition): this(type, value, value, position)

    fun mapValue(mapper: (Any) -> Any): Token {
        return Token(type, mapper(value), rawValue, position)
    }

    companion object {
        val NONE = Token(TokenType.NONE, "", TokenPosition.NONE)
    }

    override fun toString(): String {
        return "($type, »$rawValue«, $position)"
    }
}