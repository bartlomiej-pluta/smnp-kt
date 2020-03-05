package dsl.ast.model.node

import dsl.token.model.entity.Token

class TokenNode(token: Token) : Node(1, token.position) {
    val token: Any
    get() = children[0]

    init {
        children[0] = token
    }
}