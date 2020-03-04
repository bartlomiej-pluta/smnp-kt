package dsl.token.model.entity

import java.lang.RuntimeException

class TokenList(val tokens: List<Token>, val lines: List<String>) {
    private var cursor = 0
    private var snap = 0

    operator fun get(index: Int): Token {
        return tokens[index]
    }

    fun current(): Token {
        if(!hasCurrent()) {
            throw RuntimeException("Cursor points to not existing token! Cursor = ${cursor}, length = ${tokens.size}")
        }

        return tokens[cursor]
    }

    fun currentPos(): TokenPosition {
        return current().position
    }

    fun hasCurrent(): Boolean {
        return cursor < tokens.size
    }

    fun hasMore(count: Int = 1): Boolean {
        return cursor + count < tokens.size
    }

    fun next(number: Int = 1): Token {
        return tokens[cursor + number]
    }

    fun prev(number: Int = 1): Token {
        return tokens[cursor - number]
    }

    fun ahead() {
        cursor += 1
    }

    fun snapshot(): Int {
        return cursor
    }

    fun restore(snapshot: Int) {
        cursor = snapshot
    }

    fun reset() {
        cursor = 0
    }


}