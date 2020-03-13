package io.smnp.ext

interface ModuleRegistry {
    fun requestModulesForPath(path: String): ModuleDefinition?
    fun registeredModules(): List<String>
}