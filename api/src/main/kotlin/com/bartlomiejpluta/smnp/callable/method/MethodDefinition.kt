package com.bartlomiejpluta.smnp.callable.method

import com.bartlomiejpluta.smnp.callable.signature.Signature
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.type.model.Value

class MethodDefinition(val signature: Signature, val body: (Environment, Value, List<Value>) -> Value)