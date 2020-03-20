package io.smnp.ext.math

import io.smnp.ext.provider.HybridModuleProvider
import io.smnp.ext.math.function.ModuloFunction
import io.smnp.ext.math.function.RandomFunction
import io.smnp.ext.math.function.RangeFunction
import org.pf4j.Extension

@Extension
class MathModule : HybridModuleProvider("smnp.math") {
   override fun functions() = listOf(
      ModuloFunction(), RangeFunction(),
      RandomFunction()
   )
   override fun dependencies() = listOf("smnp.lang", "smnp.collection")
}