package com.bartlomiejpluta.smnp.callable.function

import com.bartlomiejpluta.smnp.callable.signature.Signature
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.type.model.Value

class FunctionDefinition(val signature: Signature, val body: (Environment, List<Value>) -> Value)