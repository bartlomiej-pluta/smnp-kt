package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

class ExtendNode(type: Node, identifier: Node, functions: Node, position: TokenPosition) : Node(3, position) {
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