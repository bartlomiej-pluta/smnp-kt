package io.smnp.ext

interface ModuleRegistry {
    fun requestModulesForPath(path: String): ModuleProvider?
    fun registeredModules(): List<String>
}