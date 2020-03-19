package io.smnp.ext

import io.smnp.ext.function.ModuloFunction
import io.smnp.ext.function.RandomFunction
import io.smnp.ext.function.RangeFunction
import org.pf4j.Extension

@Extension
class MathModule : HybridModuleProvider("smnp.math") {
   override fun functions() = listOf(ModuloFunction(), RangeFunction(), RandomFunction())
   override fun dependencies() = listOf("smnp.lang", "smnp.collection")
}