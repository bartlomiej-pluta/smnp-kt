package com.bartlomiejpluta.smnp.ext.system

import com.bartlomiejpluta.smnp.ext.provider.NativeModuleProvider
import com.bartlomiejpluta.smnp.ext.system.function.ExitFunction
import com.bartlomiejpluta.smnp.ext.system.function.SleepFunction
import org.pf4j.Extension

@Extension
class SystemModule : NativeModuleProvider("smnp.system") {
   override fun functions() = listOf(ExitFunction(), SleepFunction())
}