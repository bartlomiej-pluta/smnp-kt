package io.smnp.ext.synth

import io.smnp.type.model.Value

object ConstantEnvelopeFactory : EnvelopeFactory {
   override fun createEnvelope(config: Value) = ConstantEnvelope()
}