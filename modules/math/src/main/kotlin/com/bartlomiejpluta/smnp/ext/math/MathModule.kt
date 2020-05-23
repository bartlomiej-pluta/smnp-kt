package com.bartlomiejpluta.smnp.ext.math

import com.bartlomiejpluta.smnp.ext.math.function.ModuloFunction
import com.bartlomiejpluta.smnp.ext.math.function.RandomFunction
import com.bartlomiejpluta.smnp.ext.provider.HybridModuleProvider
import org.pf4j.Extension

@Extension
class MathModule : HybridModuleProvider("smnp.math") {
   override fun functions() = listOf(ModuloFunction(), RandomFunction())
   override fun dependencies() = listOf("smnp.lang", "smnp.collection")
}