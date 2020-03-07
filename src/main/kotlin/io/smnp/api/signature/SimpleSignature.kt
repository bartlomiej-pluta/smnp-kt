package io.smnp.api.signature

import io.smnp.api.matcher.Matcher
import io.smnp.api.model.ArgumentsList
import io.smnp.api.model.Value

class SimpleSignature(private vararg val signature: Matcher) : Signature {
    override fun parse(arguments: List<Value>): ArgumentsList {
        if (arguments.size > signature.size || arguments.size < signature.count { !it.optional }) {
            return ArgumentsList.invalid()
        }

        return ArgumentsList(signature.zip(arguments).all { (matcher, argument) ->
            matcher.match(
                argument
            )
        }, arguments)
    }

    override fun toString() = "(${signature.joinToString(", ")})"
}