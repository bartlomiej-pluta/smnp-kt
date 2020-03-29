package io.smnp.error

class InvalidSignatureException(message: String?) : SmnpException("Invalid signature", message)