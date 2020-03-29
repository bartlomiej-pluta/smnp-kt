package io.smnp.dsl.ast.model.node

class ThrowNode(value: Node) : Node(1, value.position) {
    val value: Node
        get() = children[0]

    init {
        children[0] = value
    }
}