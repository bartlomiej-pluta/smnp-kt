package io.smnp.error

import io.smnp.environment.Environment

class EnvironmentException(exception: SmnpException, val environment: Environment) : SmnpException(
   exception.friendlyName,
   "${exception.message}\n\nStack trace:\n${environment.stackTrace()}"
)