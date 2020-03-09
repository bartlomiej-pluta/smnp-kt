package io.smnp.ext.lang

import io.smnp.callable.function.Function
import io.smnp.ext.ModuleDefinition
import io.smnp.ext.lang.method.ListAccessMethod
import io.smnp.ext.lang.method.MapAccessMethod
import org.pf4j.Extension

@Extension
class LangModule : ModuleDefinition {
    override fun modulePath() = "smnp.lang"

    override fun functions() = emptyList<Function>()

    override fun methods() = listOf(ListAccessMethod(), MapAccessMethod())
}