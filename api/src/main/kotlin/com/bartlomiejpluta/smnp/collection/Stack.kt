package com.bartlomiejpluta.smnp.collection

class Stack<T> private constructor(private val list: MutableList<T>) : List<T> by list {
    fun push(item: T) {
        list.add(item)
    }

    fun pop(): T? {
        if(list.isEmpty()) {
            return null
        }

        val last = list.last()
        list.removeAt(list.size-1)
        return last
    }

    fun top() = list.last()

    companion object {
        fun <T> of(vararg items: T): Stack<T> {
            return Stack(mutableListOf(*items))
        }
    }
}