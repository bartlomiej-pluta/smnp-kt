package io.smnp.ext.lang

import io.smnp.ext.provider.NativeModuleProvider
import io.smnp.ext.lang.constructor.IntConstructor
import io.smnp.ext.lang.constructor.NoteConstructor
import io.smnp.ext.lang.function.TypeOfFunction
import io.smnp.ext.lang.method.CharAtMethod
import io.smnp.ext.lang.method.ListAccessMethod
import io.smnp.ext.lang.method.MapAccessMethod
import io.smnp.ext.lang.method.StringifyMethod
import org.pf4j.Extension

@Extension
class LangModule : NativeModuleProvider("smnp.lang") {
    override fun functions() = listOf(IntConstructor(), NoteConstructor(), TypeOfFunction())
    override fun methods() = listOf(ListAccessMethod(), MapAccessMethod(), CharAtMethod(), StringifyMethod())
}