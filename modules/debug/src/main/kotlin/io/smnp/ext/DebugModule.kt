package io.smnp.ext

import io.smnp.ext.function.StackTraceFuction
import org.pf4j.Extension

@Extension
class DebugModule : NativeModuleProvider("smnp.lang.debug") {
   override fun functions() = listOf(StackTraceFuction())
}