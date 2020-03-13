package io.smnp.runtime.model

import io.smnp.collection.Stack
import io.smnp.type.model.Value
import io.smnp.type.module.Module


class CallStack {
    private val items = Stack.of<CallStackFrame>()

    fun push(module: Module, name: String, arguments: List<Value>) {
        items.push(CallStackFrame(module, name, arguments))
    }

    fun push(frame: CallStackFrame) = items.push(frame)

    fun pop() = items.pop()

    fun top() = items.top()

    fun pretty() {
        items.asReversed().forEachIndexed { index, item -> println("[${items.size - index - 1}] $item") }
    }

    fun stackTrace() = items.asReversed().mapIndexed { index, item -> "[${items.size - index - 1}] $item" }.joinToString("\n")
}