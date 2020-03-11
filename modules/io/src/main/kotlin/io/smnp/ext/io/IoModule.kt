package io.smnp.ext.io

import io.smnp.callable.method.Method
import io.smnp.ext.ModuleDefinition
import io.smnp.ext.io.function.PrintlnFunction
import org.pf4j.Extension

@Extension
class IoModule : ModuleDefinition {
    override fun modulePath() = "smnp.io"

    override fun functions() = listOf(PrintlnFunction())

    override fun methods() = emptyList<Method>()
}