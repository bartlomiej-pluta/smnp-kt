package io.smnp.error

import io.smnp.callable.method.Method
import io.smnp.callable.signature.ActualSignatureFormatter.format
import io.smnp.environment.Environment
import io.smnp.type.model.Value

class MethodInvocationException(
   method: Method,
   obj: Value,
   passedArguments: Array<out Value>,
   val environment: Environment
) : SmnpException(
   "Method invocation error",
   "Invalid signature: $obj.${method.name}${format(passedArguments)}\nAllowed signatures:\n${method.signature}"
)