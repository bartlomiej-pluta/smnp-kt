package io.smnp.error

import io.smnp.callable.method.Method
import io.smnp.callable.signature.ActualSignatureFormatter.format
import io.smnp.type.model.Value

class MethodInvocationException(private val method: Method, private val obj: Value, private val passedArguments: Array<out Value>) : Exception() {
    override fun toString() = "Invalid signature: $obj.${method.name}${format(passedArguments)}\nAllowed signatures:\n${method.signature}"
}