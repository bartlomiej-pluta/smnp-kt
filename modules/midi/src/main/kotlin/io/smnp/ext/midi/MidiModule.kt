package io.smnp.ext.midi

import io.smnp.environment.Environment
import io.smnp.ext.provider.NativeModuleProvider
import io.smnp.ext.midi.function.MidiFunction
import io.smnp.ext.midi.function.MidiHelpFunction
import io.smnp.ext.midi.lib.midi.Midi
import org.pf4j.Extension

@Extension
class MidiModule : NativeModuleProvider("smnp.audio.midi") {
   override fun functions() = listOf(MidiFunction(), MidiHelpFunction())

   override fun dependencies() = listOf("smnp.music")

   override fun onModuleLoad(environment: Environment) {
      Midi.init()
   }

   override fun beforeModuleDisposal(environment: Environment) {
      Midi.dispose()
   }
}