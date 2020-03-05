package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

abstract class UnaryOperatorAbstractNode(position: TokenPosition) : Node(2, position) {
    var operator: Any
    get() = children[0]
    set(value) {
        children[0] = value
    }

    var value: Any
    get() = children[1]
    set(_value) {
        children[1] = _value
    }
}