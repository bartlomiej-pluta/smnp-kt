package io.smnp.ext.lang

import io.smnp.ext.ModuleDefinition
import io.smnp.ext.lang.function.DebugFunction
import io.smnp.ext.lang.method.ListAccessMethod
import io.smnp.ext.lang.method.MapAccessMethod
import org.pf4j.Extension

@Extension
class LangModule : ModuleDefinition("smnp.lang") {
    override fun functions() = listOf(DebugFunction())
    override fun methods() = listOf(ListAccessMethod(), MapAccessMethod())
}