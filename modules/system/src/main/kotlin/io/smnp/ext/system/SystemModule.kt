package io.smnp.ext.system

import io.smnp.ext.provider.NativeModuleProvider
import io.smnp.ext.system.function.ExitFunction
import io.smnp.ext.system.function.SleepFunction
import org.pf4j.Extension

@Extension
class SystemModule : NativeModuleProvider("smnp.system") {
   override fun functions() = listOf(ExitFunction(), SleepFunction())
}