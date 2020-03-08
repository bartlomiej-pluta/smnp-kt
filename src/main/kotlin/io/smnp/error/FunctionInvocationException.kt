package io.smnp.error

import io.smnp.api.callable.Function
import io.smnp.api.model.Value
import io.smnp.api.signature.ActualSignatureFormatter.format

class FunctionInvocationException(private val function: Function, private val passedArguments: Array<out Value>) : Exception() {
    override fun toString() = "Invalid signature: ${function.name}${format(passedArguments)}\nAllowed signatures:\n${function.signature}"
}