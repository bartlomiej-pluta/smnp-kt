package io.smnp.api.signature

import io.smnp.api.matcher.Matcher
import io.smnp.api.model.ArgumentsList
import io.smnp.api.model.Value

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