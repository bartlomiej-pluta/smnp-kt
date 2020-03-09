package io.smnp.ext

import io.smnp.type.module.Module
import org.pf4j.DefaultPluginManager

object ModuleRegistry {
    private val modules = mutableListOf<Pair<String, Module>>()
    init {
        val pluginManager = DefaultPluginManager()
        pluginManager.loadPlugins()
        pluginManager.startPlugins()

        pluginManager.getExtensions(ModuleDefinition::class.java).forEach {
            modules.add(Pair(it.modulePath(), Module.create(it.modulePath(), it.functions(), it.methods())))
        }
    }

    fun requestModulesForPath(path: String): List<Module> {
        return modules.filter { it.first == path }.map { it.second }
    }
}