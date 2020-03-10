package io.smnp.environment

import io.smnp.ext.DefaultModuleRegistry
import io.smnp.type.module.Module

class DefaultEnvironment : Environment {
    private val rootModule = Module("<root>")
    private val loadedModules = mutableListOf<String>()

    override fun loadModule(path: String) {
        DefaultModuleRegistry.requestModulesForPath(path).forEach {
            rootModule.addSubmodule(it)
            loadedModules.add(path)
        }
    }

    override fun printModules(printContent: Boolean) {
        rootModule.pretty(printContent)
    }
}