package io.smnp.ext

import io.smnp.ext.function.ExitFunction
import io.smnp.ext.function.SleepFunction
import org.pf4j.Extension

@Extension
class SystemModule : NativeModuleProvider("smnp.system") {
   override fun functions() = listOf(ExitFunction(), SleepFunction())
}