package com.bartlomiejpluta.smnp.ext.mic

import com.bartlomiejpluta.smnp.ext.mic.function.MicLevelFunction
import com.bartlomiejpluta.smnp.ext.mic.function.WaitFunction
import com.bartlomiejpluta.smnp.ext.provider.NativeModuleProvider
import org.pf4j.Extension

@Extension
class MicrophoneModule : NativeModuleProvider("smnp.audio.mic") {
   override fun functions() = listOf(WaitFunction(), MicLevelFunction())
}