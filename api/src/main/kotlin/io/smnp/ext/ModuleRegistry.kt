package io.smnp.ext

import io.smnp.environment.Environment

interface ModuleRegistry {
    fun requestModuleProviderForPath(path: String): ModuleProvider?
    fun registeredModules(): List<String>
    fun disposeModules(environment: Environment)
}