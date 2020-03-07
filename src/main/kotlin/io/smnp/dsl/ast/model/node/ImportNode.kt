package io.smnp.dsl.ast.model.node

import io.smnp.dsl.token.model.entity.TokenPosition

class ImportNode(path: Node, position: TokenPosition) : Node(1, position) {
    val path: Node
    get() = children[0]

    init {
        children[0] = path
    }
}