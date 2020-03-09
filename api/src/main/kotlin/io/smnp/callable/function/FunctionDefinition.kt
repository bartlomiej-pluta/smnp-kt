package io.smnp.callable.function

import io.smnp.callable.signature.Signature
import io.smnp.environment.Environment
import io.smnp.type.model.Value

class FunctionDefinition(val signature: Signature, val body: (Environment, List<Value>) -> Value)