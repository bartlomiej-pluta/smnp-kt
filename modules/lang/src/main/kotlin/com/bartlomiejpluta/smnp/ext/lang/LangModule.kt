package com.bartlomiejpluta.smnp.ext.lang

import com.bartlomiejpluta.smnp.ext.lang.constructor.IntConstructor
import com.bartlomiejpluta.smnp.ext.lang.constructor.NoteConstructor
import com.bartlomiejpluta.smnp.ext.lang.function.TypeOfFunction
import com.bartlomiejpluta.smnp.ext.lang.method.CharAtMethod
import com.bartlomiejpluta.smnp.ext.lang.method.ListAccessMethod
import com.bartlomiejpluta.smnp.ext.lang.method.MapAccessMethod
import com.bartlomiejpluta.smnp.ext.lang.method.StringifyMethod
import com.bartlomiejpluta.smnp.ext.provider.NativeModuleProvider
import org.pf4j.Extension

@Extension
class LangModule : NativeModuleProvider("smnp.lang") {
    override fun functions() = listOf(IntConstructor(), NoteConstructor(), TypeOfFunction())
    override fun methods() = listOf(ListAccessMethod(), MapAccessMethod(), CharAtMethod(), StringifyMethod())
}