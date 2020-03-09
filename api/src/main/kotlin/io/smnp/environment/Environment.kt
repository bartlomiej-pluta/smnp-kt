package io.smnp.environment

import io.smnp.type.module.Module

class Environment {
    private val rootModule = Module("<root>")
    private val loadedModules = mutableListOf<String>()

    fun loadModule(path: String) {

    }

    fun printModules(printContent: Boolean) {
        rootModule.pretty(printContent)
    }
}