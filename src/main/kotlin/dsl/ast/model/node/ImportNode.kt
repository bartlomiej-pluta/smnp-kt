package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

class ImportNode(path: Node, position: TokenPosition) : Node(1, position) {
    val path: Node
    get() = children[0]

    init {
        children[0] = path
    }
}