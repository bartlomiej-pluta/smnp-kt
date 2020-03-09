package io.smnp.callable.signature

import io.smnp.type.matcher.Matcher
import io.smnp.type.model.Value

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