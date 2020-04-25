package io.smnp.ext.musictool

import io.smnp.ext.provider.LanguageModuleProvider
import org.pf4j.Extension

@Extension
class MusicToolsModule : LanguageModuleProvider("smnp.music.tool") {
   override fun dependencies() = listOf("smnp.audio.synth", "smnp.audio.midi", "smnp.music")
}