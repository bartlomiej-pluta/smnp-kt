package io.smnp.data.signature

import io.smnp.data.enumeration.DataType
import io.smnp.data.model.Value

class Matcher(val type: DataType?, val matcher: (Value) -> Boolean, val string: String) {

    fun match(value: Value): Boolean =
        (type != null && type == value.type && matcher(value)) || (type == null) && matcher(value)

    infix fun and(matcher: Matcher): Matcher {
        if (type != matcher.type) {
            throw RuntimeException("Matchers' supported type are different to each other: $type != ${matcher.type}")
        }

        val string = "[${this.string} and ${matcher.string}]"
        return Matcher(type, { this.match(it) && matcher.match(it) }, string)
    }

    infix fun or(matcher: Matcher): Matcher {
        if (type != matcher.type) {
            throw RuntimeException("Matchers' supported type are different to each other: $type != ${matcher.type}")
        }

        val string = "[${this.string} and ${matcher.string}]"
        return Matcher(type, { this.match(it) || matcher.match(it) }, string)
    }

    override fun equals(other: Any?) = toString() == other.toString()

    override fun toString() = string

    companion object {
        fun mapOfMatchers(keyMatchers: List<Matcher>, valueMatchers: List<Matcher>): Matcher {
            return Matcher(
                DataType.MAP,
                {
                    (it.value!! as Map<Value, Value>).entries.all { (k, v) ->
                        keyMatchers.any { m -> m.match(k) } && valueMatchers.any { m ->
                            m.match(
                                v
                            )
                        }
                    }
                },
                "map<${keyMatchers.joinToString(", ") { it.toString() }}><${valueMatchers.joinToString(", ") { it.toString() }}>"
            )
        }

        fun recursiveListMatcher(matcher: Matcher): Matcher {
            if (matcher.type == DataType.LIST) {
                throw RuntimeException("Passed matcher will be handling non-list types, so it cannot have supported type of ${DataType.LIST}")
            }

            fun match(value: Value): Boolean {
                if (value.type != DataType.LIST) {
                    return matcher.match(value)
                }

                return (value.value!! as List<Value>).all { match(it) }
            }

            return Matcher(
                DataType.LIST,
                { match(it) },
                "[LIST OF $matcher]"
            )
        }

        fun listOfMatchers(vararg matchers: Matcher): Matcher {
            return Matcher(
                DataType.LIST,
                { list -> (list.value as List<Value>).all { item -> matchers.any { it.match(item) } } },
                "list<${matchers.joinToString(", ") { it.toString() }}>"
            )
        }

        fun listOf(vararg types: DataType): Matcher {
            return Matcher(
                DataType.LIST,
                { list -> (list.value as List<Value>).all { it.type in types } },
                "list<${types.joinToString(", ") { it.name.toLowerCase() }}>"
            )
        }

        fun allTypes(): Matcher {
            return Matcher(null, { it.type != DataType.VOID }, "any")
        }

        fun ofTypes(vararg types: DataType): Matcher {
            return Matcher(null, { it.type in types }, "<${types.joinToString(", ") { it.name.toLowerCase() }}>")
        }

        fun ofType(type: DataType): Matcher {
            return Matcher(null, { it.type == type }, type.name.toLowerCase())
        }

        fun oneOf(vararg matchers: Matcher): Matcher {
            return Matcher(
                null,
                { value -> matchers.any { matcher -> matcher.match(value) } },
                "<${matchers.joinToString(", ") { it.toString() }}>"
            )
        }
    }
}