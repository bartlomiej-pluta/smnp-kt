package io.smnp.api.environment

import io.smnp.api.module.Module
import io.smnp.module.loader.ModuleProvider

class Environment {
    private val rootModule = Module("<root>")
    private val loadedModules = mutableListOf<String>()

    fun loadModule(path: String) {
        val module = ModuleProvider.getModule(path)
        rootModule.addSubmodule(module)
        loadedModules.add(path)
    }

    fun printModules(printContent: Boolean) {
        rootModule.pretty(printContent)
    }
}