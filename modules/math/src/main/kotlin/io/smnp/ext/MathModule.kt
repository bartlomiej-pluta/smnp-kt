package io.smnp.ext

import io.smnp.ext.function.ModuloFunction
import io.smnp.ext.function.RangeFunction
import org.pf4j.Extension

@Extension
class MathModule : NativeModuleProvider("smnp.math") {
   override fun functions() = listOf(ModuloFunction(), RangeFunction())
}