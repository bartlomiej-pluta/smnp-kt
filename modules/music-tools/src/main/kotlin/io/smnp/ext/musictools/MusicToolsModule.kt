package io.smnp.ext.musictools

import io.smnp.ext.LanguageModuleProvider
import org.pf4j.Extension

@Extension
class MusicToolsModule : LanguageModuleProvider("smnp.music.tools") {
   override fun dependencies() = listOf("smnp.audio.midi", "smnp.music")
}