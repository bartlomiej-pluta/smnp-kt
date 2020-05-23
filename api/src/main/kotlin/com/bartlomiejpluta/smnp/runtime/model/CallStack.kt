package com.bartlomiejpluta.smnp.runtime.model

import com.bartlomiejpluta.smnp.collection.Stack
import com.bartlomiejpluta.smnp.type.model.Value
import com.bartlomiejpluta.smnp.type.module.Module


class CallStack {
   private val items = Stack.of<CallStackFrame>()

   fun push(module: Module, name: String, arguments: List<Value>) {
      items.push(CallStackFrame(module, name, arguments))
   }

   fun push(frame: CallStackFrame) = items.push(frame)

   fun pop() = items.pop()

   fun top() = items.top()

   val size: Int
      get() = items.size

   fun pretty(scopes: Boolean = false) {
      items.asReversed().mapIndexed { index, item ->
         println("[${items.size - index - 1}] $item")
         if (scopes) {
            item.scopes.forEach { println("    $it") }
         }
      }
   }

   val stackTrace: List<String>
      get() = items.asReversed().mapIndexed { index, item -> "[${items.size - index - 1}] $item" }
}