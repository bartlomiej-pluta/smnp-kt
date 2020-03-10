package io.smnp.runtime.model

import io.smnp.type.model.Value
import io.smnp.type.module.Module

class CallStack {
    private val items = mutableListOf<CallStackItem>()

    fun push(module: Module, name: String, arguments: List<Value>) {
        items.add(CallStackItem(module, name, arguments))
    }

    fun push(item: CallStackItem) {
        items.add(item)
    }

    fun pop(): CallStackItem? {
        if(items.isEmpty()) {
            return null
        }

        val last = items.last()
        items.removeAt(items.size-1)
        return last
    }

    fun pretty() {
        items.asReversed().forEachIndexed { index, item -> println("[${items.size - index - 1}] $item") }
    }
}