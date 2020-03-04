package dsl.ast.node

import dsl.token.model.entity.TokenPosition

class NoneNode : Node(0, TokenPosition.NONE)

abstract class Node(numberOfChildren: Int, val position: TokenPosition) {
    protected var children: Array<Node> = Array(numberOfChildren) { NONE }

    constructor(children: Array<Node>, position: TokenPosition) : this(children.size, position) {
        this.children = children
    }

    operator fun get(index: Int) = children[index]

    operator fun set(index: Int, value: Node) {
        children[index] = value
    }

    fun pretty(prefix: String = "", last: Boolean = true, first: Boolean = true) {
        var newPrefix = prefix
        var newLast = last
        val newFirst = first

        println(newPrefix + (if (newFirst) "" else if (newLast) "└─" else "├─") + this::class.simpleName + " " + position)
        newPrefix += if (newLast) "   " else "│  "
        for((index, child) in children.withIndex()) {
            newLast = index == children.size - 1
            child.pretty(newPrefix, newLast, false)
        }
    }

    companion object {
        val NONE = NoneNode()
    }
}