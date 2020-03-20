package io.smnp.ext

import io.smnp.environment.Environment
import io.smnp.ext.function.SynthFunction
import io.smnp.ext.function.WaveFunction
import io.smnp.ext.synth.Synthesizer
import org.pf4j.Extension

@Extension
class SynthModule : NativeModuleProvider("smnp.audio.synth") {
   override fun functions() = listOf(WaveFunction(), SynthFunction())

   override fun onModuleLoad(environment: Environment) {
      Synthesizer.init()
   }

   override fun beforeModuleDisposal(environment: Environment) {
      Synthesizer.dispose()
   }
}