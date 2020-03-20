package io.smnp.ext.registry

import io.smnp.environment.Environment
import io.smnp.ext.provider.ModuleProvider

interface ModuleRegistry {
    fun requestModuleProviderForPath(path: String): ModuleProvider?
    fun registeredModules(): List<String>
    fun disposeModules(environment: Environment)
}