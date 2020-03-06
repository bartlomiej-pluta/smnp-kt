package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

abstract class Node(numberOfChildren: Int, val position: TokenPosition) {
    var children: MutableList<Node> = MutableList(numberOfChildren) { NONE }
        protected set

    constructor(children: List<Node>, position: TokenPosition) : this(children.size, position) {
        this.children = children.toMutableList()
    }

    operator fun get(index: Int) = children[index]

    operator fun set(index: Int, value: Node) {
        children[index] = value
    }

    open fun pretty(prefix: String = "", last: Boolean = true, first: Boolean = true) {
        var newPrefix = prefix
        var newLast = last
        val nodeName = this::class.simpleName ?: "<anonymous>"

        println(newPrefix + (if (first) "" else if (newLast) "└─" else "├─") + nodeName + " " + position)
        newPrefix += if (newLast) "   " else "│  "
        for ((index, child) in children.withIndex()) {
            newLast = index == children.size - 1
            child.pretty(newPrefix, newLast, false)
            //println(newPrefix + (if (newLast) "└ " else "├ ") + child) // todo move to atom nodes
        }
    }

    companion object {
        val NONE = NoneNode()
    }
}