package com.bartlomiejpluta.smnp.error

import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenPosition

class PositionException(exception: SmnpException, val position: TokenPosition) : SmnpException(
   exception.friendlyName, exception.message, exception
)