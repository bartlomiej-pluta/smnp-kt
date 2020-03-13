package io.smnp.ext.lang

import io.smnp.ext.NativeModuleProvider
import io.smnp.ext.lang.function.TypeOfFunction
import io.smnp.ext.lang.method.ListAccessMethod
import io.smnp.ext.lang.method.MapAccessMethod
import org.pf4j.Extension

@Extension
class LangModule : NativeModuleProvider("smnp.lang") {
    override fun functions() = listOf(TypeOfFunction())
    override fun methods() = listOf(ListAccessMethod(), MapAccessMethod())
}