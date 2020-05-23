package com.bartlomiejpluta.smnp.ext.debug

import com.bartlomiejpluta.smnp.ext.debug.function.StackTraceFuction
import com.bartlomiejpluta.smnp.ext.provider.NativeModuleProvider
import org.pf4j.Extension

@Extension
class DebugModule : NativeModuleProvider("smnp.lang.debug") {
   override fun functions() = listOf(StackTraceFuction())
}