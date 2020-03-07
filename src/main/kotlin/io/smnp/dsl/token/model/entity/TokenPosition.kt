package io.smnp.dsl.token.model.entity

data class TokenPosition(val line: Int, val beginCol: Int, val endCol: Int) {
    companion object {
        val NONE = TokenPosition(-1, -1, -1)
    }

    override fun toString(): String {
        return "[line ${line+1}, col ${beginCol}]"
    }

    fun short(): String {
        return "${line+1}:${beginCol}"
    }
}