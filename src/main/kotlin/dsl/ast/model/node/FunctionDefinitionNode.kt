package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

class FunctionDefinitionNode(identifier: Node, arguments: Node, body: Node, position: TokenPosition) : Node(3, position) {
    val identifier: Node
    get() = children[0]

    val arguments: Node
    get() = children[1]

    val body: Node
    get() = children[2]

    init {
        children[0] = identifier
        children[1] = arguments
        children[2] = body
    }
}