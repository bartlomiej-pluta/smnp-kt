package io.smnp.ext.synth.lib.envelope

import io.smnp.error.CustomException
import io.smnp.type.enumeration.DataType
import io.smnp.type.matcher.Matcher
import io.smnp.type.model.Value
import io.smnp.util.config.ConfigMapSchema

interface EnvelopeFactory {
   fun createEnvelope(config: Value): Envelope

   companion object {
      private val schema = ConfigMapSchema()
         .required("name", Matcher.ofType(DataType.STRING))

      private val factories = mapOf(
         "adsr" to AdsrEnvelopeFactory,
         "const" to ConstantEnvelopeFactory
      )

      fun provideEnvelope(envelopeConfig: Value) = schema.parse(envelopeConfig)["name"].let {
         factories[it.value]?.createEnvelope(envelopeConfig) ?: throw CustomException("Unknown envelope '${it.value}'")
      }
   }
}