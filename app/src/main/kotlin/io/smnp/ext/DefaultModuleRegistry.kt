package io.smnp.ext

import io.smnp.environment.Environment
import io.smnp.error.ModuleException
import org.pf4j.DefaultPluginManager
import java.nio.file.Paths

object DefaultModuleRegistry : ModuleRegistry {
    private val MODULES_DIR = Paths.get(System.getProperty("smnp.modulesDir") ?: "modules")
    private val pluginManager = DefaultPluginManager(MODULES_DIR)
    private val modules = mutableMapOf<String, ModuleProvider>()
    init {
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

    override fun disposeModules(environment: Environment) {
        modules.forEach { (_, module) -> module.beforeModuleDisposal(environment) }
        pluginManager.stopPlugins()
    }
}