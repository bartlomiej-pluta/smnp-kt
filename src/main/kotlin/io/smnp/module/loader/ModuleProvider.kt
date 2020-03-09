package io.smnp.module.loader

import io.smnp.api.module.Module

object ModuleProvider {
    fun getModule(path: String): Module {
        // TODO: It's only stub for real module provider
        return Module.create(path)
    }
}