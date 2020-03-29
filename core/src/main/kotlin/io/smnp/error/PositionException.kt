package io.smnp.error

import io.smnp.dsl.token.model.entity.TokenPosition

class PositionException(exception: SmnpException, val position: TokenPosition) : SmnpException(
   exception.friendlyName, exception.message, exception
)