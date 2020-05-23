package com.bartlomiejpluta.smnp.ext.io

import com.bartlomiejpluta.smnp.ext.io.function.PrintlnFunction
import com.bartlomiejpluta.smnp.ext.io.function.ReadFunction
import com.bartlomiejpluta.smnp.ext.provider.NativeModuleProvider
import org.pf4j.Extension

@Extension
class IoModule : NativeModuleProvider("smnp.io") {
    override fun functions() = listOf(PrintlnFunction(), ReadFunction())
}