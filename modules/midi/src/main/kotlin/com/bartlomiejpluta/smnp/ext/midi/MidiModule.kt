package com.bartlomiejpluta.smnp.ext.midi

import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.ext.midi.function.MidiFunction
import com.bartlomiejpluta.smnp.ext.midi.function.MidiHelpFunction
import com.bartlomiejpluta.smnp.ext.midi.lib.midi.Midi
import com.bartlomiejpluta.smnp.ext.provider.HybridModuleProvider
import org.pf4j.Extension

@Extension
class MidiModule : HybridModuleProvider("smnp.audio.midi") {
   override fun files() = listOf("command.mus")

   override fun functions() = listOf(MidiFunction(), MidiHelpFunction())

   override fun dependencies() = listOf("smnp.music")

   override fun onModuleLoad(environment: Environment) {
      Midi.init()
   }

   override fun beforeModuleDisposal(environment: Environment) {
      Midi.dispose()
   }
}