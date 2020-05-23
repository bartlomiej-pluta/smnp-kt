package com.bartlomiejpluta.smnp.evaluation.util

import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenPosition
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.error.EnvironmentException
import com.bartlomiejpluta.smnp.error.EvaluationException
import com.bartlomiejpluta.smnp.error.PositionException
import com.bartlomiejpluta.smnp.error.SmnpException

object ContextExceptionFactory {
   fun contextEvaluationException(message: String, position: TokenPosition, environment: Environment): SmnpException {
      return wrapWithContext(
         EvaluationException(
            message
         ), position, environment
      )
   }

   fun wrapWithContext(exception: SmnpException, position: TokenPosition, environment: Environment): SmnpException {
      return PositionException(
         EnvironmentException(
            exception,
            environment
         ), position
      )
   }
}