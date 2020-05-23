package com.bartlomiejpluta.smnp.ext.musiccommand

import com.bartlomiejpluta.smnp.ext.provider.LanguageModuleProvider
import org.pf4j.Extension

@Extension
class MusicCommandsModule : LanguageModuleProvider("smnp.music.command") {
   override fun dependencies() = listOf("smnp.collection")
}