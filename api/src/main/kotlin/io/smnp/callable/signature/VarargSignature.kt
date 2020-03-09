package io.smnp.callable.signature

import io.smnp.type.matcher.Matcher
import io.smnp.type.model.Value

class VarargSignature(private val varargMatcher: Matcher, private vararg val signature: Matcher) :
    Signature {
    override fun parse(arguments: List<Value>): ArgumentsList {
        if ((arrayListOf(varargMatcher) + signature).any { it.optional }) {
            throw RuntimeException("Vararg signature does not support optional arguments")
        }

        if (signature.size > arguments.size) {
            return ArgumentsList.invalid()
        }

        for (i in signature.indices) {
            if (!signature[i].match(arguments[i])) {
                return ArgumentsList.invalid()
            }
        }

        for (i in signature.size until arguments.size) {
            if (!varargMatcher.match(arguments[i])) {
                return ArgumentsList.invalid()
            }
        }

        return ArgumentsList.valid(
            arguments.subList(0, signature.size) + listOf(
                Value.list(
                    arguments.subList(
                        signature.size,
                        arguments.size
                    )
                )
            )
        )
    }

    override fun toString() =
        "(${signature.joinToString(", ")}${if (signature.isNotEmpty()) ", " else ""}...$varargMatcher)"
}