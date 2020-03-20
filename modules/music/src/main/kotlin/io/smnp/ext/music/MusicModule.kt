package io.smnp.ext.music

import io.smnp.ext.provider.LanguageModuleProvider
import org.pf4j.Extension

@Extension
class MusicModule : LanguageModuleProvider("smnp.music") {
   override fun dependencies() = listOf("smnp.lang", "smnp.collection", "smnp.math")
}