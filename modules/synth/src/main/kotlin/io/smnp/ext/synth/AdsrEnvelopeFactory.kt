package io.smnp.ext.synth

import io.smnp.type.enumeration.DataType.FLOAT
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value
import io.smnp.util.config.ConfigMapSchema

object AdsrEnvelopeFactory : EnvelopeFactory {
   private val schema = ConfigMapSchema()
      .required("p1", ofType(FLOAT))
      .required("p2", ofType(FLOAT))
      .required("p3", ofType(FLOAT))
      .required("s", ofType(FLOAT))

   override fun createEnvelope(config: Value) = config.let { schema.parse(it) }.let {
      AdsrEnvelope(
         (it["p1"].unwrap() as Float).toDouble(),
         (it["p2"].unwrap() as Float).toDouble(),
         (it["p3"].unwrap() as Float).toDouble(),
         (it["s"].unwrap() as Float).toDouble()
      )
   }
}