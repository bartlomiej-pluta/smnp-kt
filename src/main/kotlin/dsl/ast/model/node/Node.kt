package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

abstract class Node(numberOfChildren: Int, val position: TokenPosition) {
    var children: MutableList<Any> = MutableList(numberOfChildren) { NONE }
        protected set

    constructor(children: List<Any>, position: TokenPosition) : this(children.size, position) {
        this.children = children.toMutableList()
    }

    operator fun get(index: Int) = children[index]

    operator fun set(index: Int, value: Any) {
        children[index] = value
    }

    fun pretty(prefix: String = "", last: Boolean = true, first: Boolean = true) {
        var newPrefix = prefix
        var newLast = last
        val nodeName = this::class.simpleName ?: "<anonymous>"

        println(newPrefix + (if (first) "" else if (newLast) "└─" else "├─") + nodeName + " " + position)
        newPrefix += if (newLast) "   " else "│  "
        for ((index, child) in children.withIndex()) {
            newLast = index == children.size - 1
            if (child is Node) {
                child.pretty(newPrefix, newLast, false)
            } else {
                println(newPrefix + (if (newLast) "└ " else "├ ") + child)
            }
        }
    }

    companion object {
        val NONE = NoneNode()
    }
}