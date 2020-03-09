package io.smnp.callable.method

import io.smnp.callable.signature.Signature
import io.smnp.environment.Environment
import io.smnp.type.model.Value

class MethodDefinition(val signature: Signature, val body: (Environment, Value, List<Value>) -> Value)