package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

abstract class AtomAbstractNode(position: TokenPosition) : Node(1, position) {
    var value: Any
    get() = children[0]
    set(value) {
        children[0] = value
    }
}

