package io.smnp.environment

import io.smnp.ext.ModuleRegistry
import io.smnp.type.module.Module

interface Environment {
    fun loadModule(path: String)
    fun printModules(printContent: Boolean)
}