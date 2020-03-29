package io.smnp.evaluation.util

import io.smnp.dsl.token.model.entity.TokenPosition
import io.smnp.environment.Environment
import io.smnp.error.EnvironmentException
import io.smnp.error.EvaluationException
import io.smnp.error.PositionException
import io.smnp.error.SmnpException

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