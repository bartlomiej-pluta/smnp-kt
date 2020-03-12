package io.smnp.ext.io

import io.smnp.ext.ModuleDefinition
import io.smnp.ext.io.function.PrintlnFunction
import org.pf4j.Extension

@Extension
class IoModule : ModuleDefinition("smnp.io") {
    override fun functions() = listOf(PrintlnFunction())
}