package com.bartlomiejpluta.smnp.ext.dsp

import com.bartlomiejpluta.smnp.ext.dsp.function.FftFunction
import com.bartlomiejpluta.smnp.ext.dsp.function.PlotFunction
import com.bartlomiejpluta.smnp.ext.provider.NativeModuleProvider
import org.pf4j.Extension

@Extension
class DspModule : NativeModuleProvider("smnp.dsp") {
   override fun functions() = listOf(PlotFunction(), FftFunction())
}