package io.smnp.api.model

class ArgumentsList(val signatureMatched: Boolean, val arguments: List<Value>) {
    operator fun get(index: Int) = arguments[index]

    fun toArray() = arguments.toTypedArray()

    override fun toString() = if(signatureMatched) "valid($arguments)" else "invalid"

    companion object {
        fun valid(arguments: List<Value>): ArgumentsList {
            return ArgumentsList(true, arguments)
        }

        fun invalid(): ArgumentsList {
            return ArgumentsList(false, emptyList())
        }
    }
}