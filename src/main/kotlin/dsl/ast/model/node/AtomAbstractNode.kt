package dsl.ast.model.node

import dsl.token.model.entity.Token

abstract class AtomAbstractNode(token: Token) : Node(1, token.position) {
    var value: Any
    get() = children[0]
    set(value) {
        children[0] = value
    }

    init {
        value = token.value
    }
}

