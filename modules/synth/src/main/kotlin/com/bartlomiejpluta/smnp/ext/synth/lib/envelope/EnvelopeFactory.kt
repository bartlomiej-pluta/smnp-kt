package com.bartlomiejpluta.smnp.ext.synth.lib.envelope

import com.bartlomiejpluta.smnp.error.CustomException
import com.bartlomiejpluta.smnp.type.enumeration.DataType
import com.bartlomiejpluta.smnp.type.matcher.Matcher
import com.bartlomiejpluta.smnp.type.model.Value
import com.bartlomiejpluta.smnp.util.config.ConfigMapSchema

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