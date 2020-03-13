package io.smnp.error

abstract class SmnpException(val friendlyName: String, message: String? = null) : Exception(message)