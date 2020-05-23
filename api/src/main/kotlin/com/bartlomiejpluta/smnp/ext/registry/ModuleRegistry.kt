package com.bartlomiejpluta.smnp.ext.registry

import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.ext.provider.ModuleProvider

interface ModuleRegistry {
    fun requestModuleProviderForPath(path: String): ModuleProvider?
    fun registeredModules(): List<String>
    fun disposeModules(environment: Environment)
}