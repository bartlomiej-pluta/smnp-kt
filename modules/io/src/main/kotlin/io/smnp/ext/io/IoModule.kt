package io.smnp.ext.io

import io.smnp.ext.NativeModuleProvider
import io.smnp.ext.io.function.PrintlnFunction
import org.pf4j.Extension

@Extension
class IoModule : NativeModuleProvider("smnp.io") {
    override fun functions() = listOf(PrintlnFunction())
}