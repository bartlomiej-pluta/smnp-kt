package io.smnp.dsl.ast.model.node

import io.smnp.dsl.token.model.entity.TokenPosition

class ExtendNode(type: Node, identifier: Node, functions: Node, position: TokenPosition) : Node(3, position) {
    operator fun component1(): Node = children[0]
    operator fun component2(): Node = children[1]
    operator fun component3(): Node = children[2]

    val type: Node
    get() = children[0]

    val identifier: Node
    get() = children[1]

    val functions: Node
    get() = children[2]

    init {
        children[0] = type
        children[1] = identifier
        children[2] = functions
    }
}