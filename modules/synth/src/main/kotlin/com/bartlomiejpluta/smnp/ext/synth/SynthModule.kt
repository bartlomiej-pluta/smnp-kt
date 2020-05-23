package com.bartlomiejpluta.smnp.ext.synth

import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.ext.provider.HybridModuleProvider
import com.bartlomiejpluta.smnp.ext.synth.function.SynthFunction
import com.bartlomiejpluta.smnp.ext.synth.function.WaveFunction
import com.bartlomiejpluta.smnp.ext.synth.lib.synthesizer.Synthesizer
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