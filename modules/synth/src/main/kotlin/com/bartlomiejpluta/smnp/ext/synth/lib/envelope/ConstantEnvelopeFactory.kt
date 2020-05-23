package com.bartlomiejpluta.smnp.ext.synth.lib.envelope

import com.bartlomiejpluta.smnp.type.model.Value

object ConstantEnvelopeFactory : EnvelopeFactory {
   override fun createEnvelope(config: Value) = ConstantEnvelope()
}