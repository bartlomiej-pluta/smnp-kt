package io.smnp.ext

interface ModuleRegistry {
    fun requestModuleProviderForPath(path: String): ModuleProvider?
    fun registeredModules(): List<String>
}