package io.smnp.environment

import io.smnp.ext.ModuleRegistry
import io.smnp.type.module.Module

class Environment {
    private val rootModule = Module("<root>")
    private val loadedModules = mutableListOf<String>()

    fun loadModule(path: String) {
        ModuleRegistry.requestModulesForPath(path).forEach {
            rootModule.addSubmodule(it)
            loadedModules.add(path)
        }
    }

    fun printModules(printContent: Boolean) {
        rootModule.pretty(printContent)
    }
}