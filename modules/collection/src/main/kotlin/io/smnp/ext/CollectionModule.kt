package io.smnp.ext

import org.pf4j.Extension

@Extension
class CollectionModule : LanguageModuleProvider("smnp.collection") {
   override fun files() = listOf("list.mus")
   override fun dependencies() = listOf("smnp.lang")
}