package io.smnp.ext.musiccommand

import io.smnp.ext.provider.LanguageModuleProvider
import org.pf4j.Extension

@Extension
class MusicCommandsModule : LanguageModuleProvider("smnp.music.command") {
   override fun dependencies() = listOf("smnp.collection")
}