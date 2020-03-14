package io.smnp.ext

import io.smnp.environment.Environment
import io.smnp.ext.function.MidiFunction
import io.smnp.ext.midi.MidiSequencer
import org.pf4j.Extension

@Extension
class MidiModule : NativeModuleProvider("smnp.audio.midi") {
   override fun functions() = listOf(MidiFunction())

   override fun onModuleLoad(environment: Environment) {
      MidiSequencer.init()
   }

   override fun beforeModuleDisposal(environment: Environment) {
      MidiSequencer.dispose()
   }
}