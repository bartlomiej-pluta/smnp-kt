package io.smnp.error

import io.smnp.api.callable.Method
import io.smnp.api.model.Value
import io.smnp.api.signature.ActualSignatureFormatter.format

class MethodInvocationException(private val method: Method, private val obj: Value, private val passedArguments: Array<out Value>) : Exception() {
    override fun toString() = "Invalid signature: $obj.${method.name}${format(passedArguments)}\nAllowed signatures:\n${method.signature}"
}