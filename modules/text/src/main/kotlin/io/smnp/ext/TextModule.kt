package io.smnp.ext

import org.pf4j.Extension

@Extension
class TextModule : LanguageModuleProvider("smnp.text") {
   override fun dependencies() = listOf("smnp.lang", "smnp.collection")
}