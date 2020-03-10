package io.smnp.runtime.model

import io.smnp.callable.signature.ActualSignatureFormatter
import io.smnp.type.model.Value
import io.smnp.type.module.Module

data class CallStackItem(
    val module: Module,
    val name: String,
    val arguments: List<Value>
) {
    override fun toString() = "${module.canonicalName}::$name${ActualSignatureFormatter.format(arguments.toTypedArray())}"
}