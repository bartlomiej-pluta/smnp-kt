package io.smnp.error

class CustomException(message: String?) : SmnpException("Error", message)