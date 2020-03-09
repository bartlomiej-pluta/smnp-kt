package io.smnp.error

import io.smnp.callable.function.Function
import io.smnp.callable.signature.ActualSignatureFormatter.format
import io.smnp.type.model.Value

class FunctionInvocationException(private val function: Function, private val passedArguments: Array<out Value>) : Exception() {
    override fun toString() = "Invalid signature: ${function.name}${format(passedArguments)}\nAllowed signatures:\n${function.signature}"
}