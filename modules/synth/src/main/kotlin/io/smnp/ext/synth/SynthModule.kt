package io.smnp.ext.synth

import io.smnp.environment.Environment
import io.smnp.ext.provider.HybridModuleProvider
import io.smnp.ext.synth.function.SynthFunction
import io.smnp.ext.synth.function.WaveFunction
import io.smnp.ext.synth.lib.synthesizer.Synthesizer
import org.pf4j.Extension

@Extension
class SynthModule : HybridModuleProvider("smnp.audio.synth") {
   override fun functions() = listOf(WaveFunction(), SynthFunction())

   override fun files() = listOf("envelope.mus", "command.mus")

   override fun onModuleLoad(environment: Environment) {
      Synthesizer.init()
   }

   override fun beforeModuleDisposal(environment: Environment) {
      Synthesizer.dispose()
   }
}