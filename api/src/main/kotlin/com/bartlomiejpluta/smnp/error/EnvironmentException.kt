package com.bartlomiejpluta.smnp.error

import com.bartlomiejpluta.smnp.environment.Environment

class EnvironmentException(exception: SmnpException, val environment: Environment) : SmnpException(
   exception.friendlyName, exception.message, exception
)