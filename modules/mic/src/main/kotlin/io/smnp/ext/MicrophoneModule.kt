package io.smnp.ext

import io.smnp.ext.function.MicLevelFunction
import io.smnp.ext.function.WaitFunction
import org.pf4j.Extension

@Extension
class MicrophoneModule : NativeModuleProvider("smnp.audio.mic") {
   override fun functions() = listOf(WaitFunction(), MicLevelFunction())
}