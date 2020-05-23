package com.bartlomiejpluta.smnp.dsl.ast.model.node

import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenPosition

class ExtendNode(type: Node, functions: Node, position: TokenPosition) : Node(2, position) {
    operator fun component1(): Node = children[0]
    operator fun component2(): Node = children[1]

    val type: Node
    get() = children[0]

    val functions: Node
    get() = children[1]

    init {
        children[0] = type
        children[1] = functions
    }
}