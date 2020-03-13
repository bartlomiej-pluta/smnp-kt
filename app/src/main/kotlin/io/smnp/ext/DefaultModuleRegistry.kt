package io.smnp.ext

import org.pf4j.DefaultPluginManager

object DefaultModuleRegistry : ModuleRegistry {
    private val modules = mutableMapOf<String, ModuleDefinition>()
    init {
        val pluginManager = DefaultPluginManager()
        pluginManager.loadPlugins()
        pluginManager.startPlugins()

        pluginManager.getExtensions(ModuleDefinition::class.java).forEach {
            modules[it.path] = it
        }
    }

    override fun requestModulesForPath(path: String): ModuleDefinition {
        return modules[path] ?: throw RuntimeException("Module $path not found")
    }

    override fun registeredModules() = modules.keys.toList()
}