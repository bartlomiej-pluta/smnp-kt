package io.smnp.ext.dsp

import io.smnp.ext.dsp.function.FftFunction
import io.smnp.ext.dsp.function.PlotFunction
import io.smnp.ext.provider.NativeModuleProvider
import org.pf4j.Extension

@Extension
class DspModule : NativeModuleProvider("smnp.dsp") {
   override fun functions() = listOf(PlotFunction(), FftFunction())
}