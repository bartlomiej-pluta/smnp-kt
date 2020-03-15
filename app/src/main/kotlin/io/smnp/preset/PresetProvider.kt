package io.smnp.preset

import io.smnp.error.PresetException
import java.io.File
import java.nio.file.Paths

object PresetProvider {
   private const val PRESETS_DIR = "presets"
   private val classLoader = javaClass.classLoader

   val presets: List<String>
      get() = classLoader
         .getResource("presets.index")
         ?.readText()
         ?.trim()
         ?.split("\n")
         ?.map { File(it).nameWithoutExtension }
         ?: throw RuntimeException("Presets index not found")

   fun providePresetCode(name: String) = classLoader
      .getResource(Paths.get(PRESETS_DIR, "$name.mus").toString())
      ?.readText()
      ?: throw PresetException("Preset with name '$name' does not exist")
}