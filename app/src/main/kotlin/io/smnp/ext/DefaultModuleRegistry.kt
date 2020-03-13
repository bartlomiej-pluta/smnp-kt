package io.smnp.ext

import io.smnp.error.ModuleException
import org.pf4j.DefaultPluginManager

object DefaultModuleRegistry : ModuleRegistry {
    private val modules = mutableMapOf<String, ModuleProvider>()
    init {
        val pluginManager = DefaultPluginManager()
        pluginManager.loadPlugins()
        pluginManager.startPlugins()

        pluginManager.getExtensions(ModuleProvider::class.java).forEach {
            modules[it.path] = it
        }
    }

    override fun requestModuleProviderForPath(path: String): ModuleProvider {
        return modules[path] ?: throw ModuleException("Module $path not found")
    }

    override fun registeredModules() = modules.keys.toList()
}