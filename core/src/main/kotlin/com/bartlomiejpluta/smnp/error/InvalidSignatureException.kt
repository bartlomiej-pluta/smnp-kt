package com.bartlomiejpluta.smnp.error

class InvalidSignatureException(message: String?) : SmnpException("Invalid signature", message)