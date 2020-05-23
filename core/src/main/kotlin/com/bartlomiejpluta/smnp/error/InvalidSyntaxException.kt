package com.bartlomiejpluta.smnp.error

class InvalidSyntaxException(message: String?) : SmnpException("Syntax error", message)