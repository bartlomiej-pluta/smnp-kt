package io.smnp.ext.lang

import io.smnp.callable.method.Method
import io.smnp.ext.ModuleDefinition
import org.pf4j.Extension

@Extension
class LangModule : ModuleDefinition {
    override fun modulePath() = "smnp.io"

    override fun functions() = listOf(DisplayFunction())

    override fun methods() = emptyList<Method>()
}