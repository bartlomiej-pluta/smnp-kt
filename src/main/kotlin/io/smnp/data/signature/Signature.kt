package io.smnp.data.signature

import io.smnp.data.model.Value

interface Signature {
    fun parse(arguments: List<Value>): ArgumentsList

    companion object {
        fun simple(vararg signature: Matcher): Signature {
            return SimpleSignature(*signature)
        }

        fun vararg(varargMatcher: Matcher, vararg signature: Matcher): Signature {
            return VarargSignature(varargMatcher, *signature)
        }
    }
}