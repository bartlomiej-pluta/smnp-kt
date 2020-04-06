package io.smnp.ext.audio

import io.smnp.ext.provider.LanguageModuleProvider
import org.pf4j.Extension

@Extension
class MusicCommandsModule : LanguageModuleProvider("smnp.music.commands") {
   override fun dependencies() = listOf("smnp.collection")
}