package io.smnp.ext.mic

import io.smnp.ext.provider.NativeModuleProvider
import io.smnp.ext.mic.function.MicLevelFunction
import io.smnp.ext.mic.function.WaitFunction
import org.pf4j.Extension

@Extension
class MicrophoneModule : NativeModuleProvider("smnp.audio.mic") {
   override fun functions() = listOf(WaitFunction(), MicLevelFunction())
}