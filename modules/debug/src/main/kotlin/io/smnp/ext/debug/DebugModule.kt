package io.smnp.ext.debug

import io.smnp.ext.provider.NativeModuleProvider
import io.smnp.ext.debug.function.StackTraceFuction
import org.pf4j.Extension

@Extension
class DebugModule : NativeModuleProvider("smnp.lang.debug") {
   override fun functions() = listOf(StackTraceFuction())
}