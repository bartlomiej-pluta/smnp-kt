package io.smnp.dsl.ast.model.node

class ReturnNode(value: Node) : Node(1, value.position) {
    operator fun component1() = children[0]

    val value: Node
    get() = children[0]

    init {
        children[0] = value
    }
}