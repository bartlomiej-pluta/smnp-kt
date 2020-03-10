package io.smnp.ext

import io.smnp.type.module.Module
import org.pf4j.DefaultPluginManager

interface ModuleRegistry {
    fun requestModulesForPath(path: String): List<Module>
}