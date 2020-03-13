package io.smnp.error

import io.smnp.callable.function.Function
import io.smnp.callable.signature.ActualSignatureFormatter.format
import io.smnp.environment.Environment
import io.smnp.type.model.Value

class FunctionInvocationException(
   function: Function,
   passedArguments: Array<out Value>,
   val environment: Environment
) : SmnpException(
   "Function invocation error",
   "Invalid signature: ${function.name}${format(passedArguments)}\nAllowed signatures:\n${function.signature}"
)