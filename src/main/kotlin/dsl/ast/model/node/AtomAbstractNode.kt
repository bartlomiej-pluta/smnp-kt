package dsl.ast.model.node

import dsl.token.model.entity.Token

abstract class AtomAbstractNode(token: Token) : Node(1, token.position) {
    val value: Any
    get() = children[0]

    init {
        children[0] = token.value
    }
}

