package io.smnp.api.callable

import io.smnp.api.environment.Environment
import io.smnp.api.model.Value
import io.smnp.api.signature.Signature

class MethodDefinition(val signature: Signature, val body: (Environment, Value, List<Value>) -> Value)