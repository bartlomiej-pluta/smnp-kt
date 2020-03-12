package io.smnp.ext

import io.smnp.type.module.Module
import org.pf4j.DefaultPluginManager

object DefaultModuleRegistry : ModuleRegistry {
    private val modules = mutableListOf<Pair<String, Module>>()
    init {
        val pluginManager = DefaultPluginManager()
        pluginManager.loadPlugins()
        pluginManager.startPlugins()

        pluginManager.getExtensions(ModuleDefinition::class.java).forEach {
            modules.add(it.path to it.module())
        }
    }

    override fun requestModulesForPath(path: String): List<Module> {
        return modules.filter { it.first == path }.map { it.second }
    }
}