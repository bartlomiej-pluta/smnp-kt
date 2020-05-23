package com.bartlomiejpluta.smnp.ext.collection

import com.bartlomiejpluta.smnp.ext.provider.LanguageModuleProvider
import org.pf4j.Extension

@Extension
class CollectionModule : LanguageModuleProvider("smnp.collection") {
   override fun files() = listOf("list.mus", "map.mus")
   override fun dependencies() = listOf("smnp.lang")
}