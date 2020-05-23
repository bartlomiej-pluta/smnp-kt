package com.bartlomiejpluta.smnp.ext.text

import com.bartlomiejpluta.smnp.ext.provider.LanguageModuleProvider
import org.pf4j.Extension

@Extension
class TextModule : LanguageModuleProvider("smnp.text") {
   override fun dependencies() = listOf("smnp.lang", "smnp.collection")
}